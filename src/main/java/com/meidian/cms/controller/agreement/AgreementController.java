package com.meidian.cms.controller.agreement;

import com.meidian.cms.common.Enum.ErrorCode;
import com.meidian.cms.common.Result;
import com.meidian.cms.common.ServiceResult;
import com.meidian.cms.common.Strings;
import com.meidian.cms.common.exception.BusinessException;
import com.meidian.cms.common.utils.CollectionUtil;
import com.meidian.cms.common.utils.ResultUtils;
import com.meidian.cms.common.utils.ServiceResultUtil;
import com.meidian.cms.common.utils.TimeUtil;
import com.meidian.cms.controller.basic.BaseFeeExportExcel;
import com.meidian.cms.controller.basic.BasicController;
import com.meidian.cms.controller.customer.CustomerController;
import com.meidian.cms.serviceClient.agreement.Contract;
import com.meidian.cms.serviceClient.agreement.service.ContractService;
import com.meidian.cms.serviceClient.car.CarInfo;
import com.meidian.cms.serviceClient.car.service.CarInfoService;
import com.meidian.cms.serviceClient.company.Company;
import com.meidian.cms.serviceClient.customer.Client;
import com.meidian.cms.serviceClient.customer.service.ClientService;
import com.meidian.cms.serviceClient.fee.FeeInfo;
import com.meidian.cms.serviceClient.fee.service.FeeInfoService;
import com.meidian.cms.serviceClient.user.User;
import com.meidian.cms.vo.TestVo;
import com.meidian.cms.vo.exportClientCar.ExportClientCarVo;
import com.meidian.cms.vo.feeInfo.FeeInfoVo;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.jpa.criteria.BasicPathUsageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/agreement")
public class AgreementController  extends BasicController {
    private static final Logger logger = LoggerFactory.getLogger(AgreementController.class);

    @Autowired
    private ContractService contractService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CarInfoService carInfoService;

    @Autowired
    private FeeInfoService feeInfoService;

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) throws BusinessException{
        ModelAndView mv = new ModelAndView();
        mv.addObject("companyList", getCompanyByUser(getUser(request)));
        mv.setViewName("agreement/index");
        return mv;
    }

    @ResponseBody
    @RequestMapping("/getData")
    public Result<Contract> getData(HttpServletRequest request,Integer page,Integer limit,
                                        Contract contract,String beginTime,String endTime) throws BusinessException{
        User user = getUser(request);
        PageRequest pageRequest = new PageRequest(page-1,limit);
        Integer begin = null,end = null;
        if (!StringUtils.isEmpty(beginTime)){
            begin = TimeUtil.getDayUnixTimeStamp(beginTime);
        }
        if (!StringUtils.isEmpty(endTime)){
            end = TimeUtil.getDayUnixTimeStamp(endTime);
        }
        List<Long> companyIds = new ArrayList<>();
        this.getCompanyIds(user,contract,companyIds);
        Page<Contract> carInfoResult = contractService.getPageByContract(pageRequest,contract,companyIds,begin,end);
        return ResultUtils.returnTrue(carInfoResult);
    }

    private void getCompanyIds(User user,Contract contract, List<Long> companyIds) throws BusinessException{
        if (null == contract.getCompanyId()){
            List<Company> companyList = getCompanyByUser(user);
            if (!CollectionUtil.isEmpty(companyList)){
                companyIds.addAll(companyList.stream().map(Company::getId).collect(Collectors.toList()));
            }
        }else{
            companyIds.add(contract.getCompanyId());
        }
    }

    @ResponseBody
    @RequestMapping("/getCustomersAndCarsByCompanyId")
    public ServiceResult<Map<String,Object>> getCustomersAndCarsByCompanyId(HttpServletRequest request, Long companyId) throws BusinessException{
        Map<String,Object> result = new HashMap<>();
        //获取人员信息
        ServiceResult<List<Client>> clientResult = clientService.getClientByCompanyId(companyId);
        //获取车辆信息
        ServiceResult<List<CarInfo>> carResult = carInfoService.getCarInfoByCompanyId(companyId);
        result.put("clients",clientResult.getBody());
        result.put("cars",carResult.getBody());
        return ServiceResultUtil.returnTrue(result);
    }

    @ResponseBody
    @RequestMapping("/add")
    public Result<Boolean> add(HttpServletRequest request,Contract contract,String contractTimeStr) throws BusinessException {
        User user = getUser(request);
        List<Long> companyIds = new ArrayList<>();
        this.makeDataForAdd(user,contract,contractTimeStr);
        ServiceResult<Boolean> serviceResult = contractService.addContract(contract);
        if (!serviceResult.getSuccess()){
            return ResultUtils.returnFalse(serviceResult.getErrorCode(),serviceResult.getMessage());
        }
        return ResultUtils.returnTrue(serviceResult.getMessage());
    }

    private void makeDataForAdd(User user, Contract contract, String contractTimeStr) throws BusinessException {
        List<Company> companyList = getCompanyByUser(user);
        Map<Long,String> companyMap = companyList.stream().collect(Collectors.toMap(Company::getId,Company::getCompanyName));

        //获取客户信息
        ServiceResult<Client> clientServiceResult = clientService.getClientById(contract.getUserId());
        if (!clientServiceResult.getSuccess() || clientServiceResult.getBody() == null){
            throw new BusinessException("获取客户信息失败！",ErrorCode.BUSINESS_DEFAULT_ERROR.getCode());
        }
        Client client = clientServiceResult.getBody();

        //获取车辆信息
        ServiceResult<CarInfo> carInfoServiceResult = carInfoService.getCarInfoById(contract.getCarId());
        if (!carInfoServiceResult.getSuccess()|| null == carInfoServiceResult.getBody()){
            throw new BusinessException("获取车辆信息失败！",ErrorCode.BUSINESS_DEFAULT_ERROR.getCode());
        }
        CarInfo carInfo = carInfoServiceResult.getBody();

        contract.setBusNumber(carInfo.getBusNumber());
        contract.setContractTime(TimeUtil.getDayUnixTimeStamp(contractTimeStr));
        contract.setMobile(client.getMobile());
        contract.setUserName(client.getName());
        contract.setcUName(user.getName());
        contract.setcU(user.getId());
        contract.setuUName(user.getName());
        contract.setuU(user.getId());
        contract.setuT(TimeUtil.getNowTimeStamp());
        contract.setcT(TimeUtil.getNowTimeStamp());
        contract.setCompanyName(companyMap.get(contract.getCompanyId()));
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Result<Boolean> delete(HttpServletRequest request,Contract contract) throws BusinessException {
        User user = getUser(request);
        this.makeDataForDelete(user,contract);
        ServiceResult<Boolean> serviceResult = contractService.deleteContract(contract);
        if (!serviceResult.getSuccess()){
            return ResultUtils.returnFalse(serviceResult.getErrorCode(),serviceResult.getMessage());
        }
        return ResultUtils.returnTrue(serviceResult.getMessage());
    }

    private void makeDataForDelete(User user, Contract contract) throws BusinessException{
        List<Company> companyList = getCompanyByUser(user);
        contract.setuUName(user.getName());
        contract.setuU(user.getId());
        contract.setuT(TimeUtil.getNowTimeStamp());
        contract.setIsDeleted(1);
    }


    @RequestMapping("/feeIndex")
    public ModelAndView feeIndex(HttpServletRequest request,Long contractId) throws BusinessException{
        ModelAndView mv = new ModelAndView();
        mv.addObject("contractId", contractId);
        mv.setViewName("agreement/fee");
        return mv;
    }


    @ResponseBody
    @RequestMapping("/getFeeData")
    public Result<FeeInfo> getFeeData(HttpServletRequest request, Long contractId) throws BusinessException {
        ServiceResult<List<FeeInfo>> feeListServiceResult = feeInfoService.getFeeInfoByContractId(contractId);
        return ResultUtils.returnTrue(feeListServiceResult.getBody());
    }

    @ResponseBody
    @RequestMapping("/addFeeInfo")
    public Result<FeeInfo> addFeeInfo(HttpServletRequest request, FeeInfoVo feeInfoVo) throws BusinessException {
        User user = getUser(request);
        this.makeDataForAddFeeInfo(feeInfoVo,user);
        FeeInfo feeInfo = new FeeInfo();
        BeanUtils.copyProperties(feeInfoVo,feeInfo);
        ServiceResult<Boolean> feeListServiceResult = feeInfoService.addFeeInfo(feeInfo);
        return ResultUtils.returnTrue(feeListServiceResult.getMessage());
    }

    private void makeDataForAddFeeInfo(FeeInfoVo feeInfo,User user) {

        if (Strings.isNotEmpty(feeInfo.getGradeInsuranceFeeExpireTimeStr())){
            feeInfo.setGradeInsuranceFeeTime(TimeUtil.getDayUnixTimeStamp(feeInfo.getGradeInsuranceFeeExpireTimeStr()));
        }
        if (Strings.isNotEmpty(feeInfo.getManageFeeTimeStr())){
            feeInfo.setManageFeeTime(TimeUtil.getDayUnixTimeStamp(feeInfo.getManageFeeExpireTimeStr()));
        }
        if (Strings.isNotEmpty(feeInfo.getManageFeeExpireTimeStr())){
            feeInfo.setManageFeeExpireTime(TimeUtil.getDayUnixTimeStamp(feeInfo.getManageFeeExpireTimeStr()));
        }
        if (Strings.isNotEmpty(feeInfo.getGradeInsuranceFeeTimeStr())){
            feeInfo.setGradeInsuranceFeeTime(TimeUtil.getDayUnixTimeStamp(feeInfo.getGradeInsuranceFeeTimeStr()));
        }
        if (Strings.isNotEmpty(feeInfo.getGradeInsuranceFeeExpireTimeStr())){
            feeInfo.setGradeInsuranceFeeExpireTime(TimeUtil.getDayUnixTimeStamp(feeInfo.getGradeInsuranceFeeExpireTimeStr()));
        }
        if (Strings.isNotEmpty(feeInfo.getVehicleFeeTimeStr())){
            feeInfo.setVehicleFeeTime(TimeUtil.getDayUnixTimeStamp(feeInfo.getVehicleFeeTimeStr()));
        }
        if (Strings.isNotEmpty(feeInfo.getVehicleFeeExpireTimeStr())){
            feeInfo.setVehicleFeeExpireTime(TimeUtil.getDayUnixTimeStamp(feeInfo.getVehicleFeeExpireTimeStr()));
        }
        if (Strings.isNotEmpty(feeInfo.getThreeInsuranceFeeTimeStr())){
            feeInfo.setThreeInsuranceFeeTime(TimeUtil.getDayUnixTimeStamp(feeInfo.getThreeInsuranceFeeTimeStr()));
        }
        if (Strings.isNotEmpty(feeInfo.getThreeInsuranceFeeExpireTimeStr())){
            feeInfo.setThreeInsuranceFeeExpireTime(TimeUtil.getDayUnixTimeStamp(feeInfo.getThreeInsuranceFeeExpireTimeStr()));
        }

        feeInfo.setcUName(user.getName());
        feeInfo.setcU(user.getId());
        feeInfo.setuUName(user.getName());
        feeInfo.setuU(user.getId());
        feeInfo.setuT(TimeUtil.getNowTimeStamp());
        feeInfo.setcT(TimeUtil.getNowTimeStamp());
    }

    @RequestMapping("/export")
    public void export(HttpServletRequest request, HttpServletResponse response,
                                          Contract contract,String beginTime,String endTime) throws BusinessException{
        Result<Contract> contractListResult = this.getData(request,1,Integer.MAX_VALUE,contract,beginTime,endTime);
        List<ExportClientCarVo> voList = this.makeVoFoExport(contractListResult.getData());

        /*导出excel操作*/
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("人员");
        HSSFCellStyle style4title = BaseFeeExportExcel.getColumnTopStyle(workbook);
        String[] excelTitles = {"序号","客户姓名","所属公司","手机号","地址","身份证","车牌号","发动机号","车架号","营运证"};
        Row titleRow = sheet.createRow(0);
        for(int i=0;i<excelTitles.length;i++){
            Cell titleCell = titleRow.createCell(i);
            titleCell.setCellValue(excelTitles[i]);
            titleCell.setCellStyle(style4title);
        }
        HSSFCellStyle style4data = BaseFeeExportExcel.getStyle(workbook);
        for (int i = 0; i < voList.size(); i++) {
            ExportClientCarVo rr = voList.get(i);
            Row dataRow = sheet.createRow(i+1);

            int cellIndex = 0;
            setCellValue(dataRow.createCell(cellIndex++), i+1+"", style4data);
            setCellValue(dataRow.createCell(cellIndex++), rr.getName(), style4data);
            setCellValue(dataRow.createCell(cellIndex++), rr.getCompanyName(), style4data);
            setCellValue(dataRow.createCell(cellIndex++), rr.getMobile(), style4data);
            setCellValue(dataRow.createCell(cellIndex++), rr.getAddress(), style4data);
            setCellValue(dataRow.createCell(cellIndex++), rr.getIdentifyNumber(), style4data);
            setCellValue(dataRow.createCell(cellIndex++), rr.getBusNumber(), style4data);
            setCellValue(dataRow.createCell(cellIndex++), rr.getEngineNumber(), style4data);
            setCellValue(dataRow.createCell(cellIndex++), rr.getVehicleIdentificationNumber(), style4data);
            setCellValue(dataRow.createCell(cellIndex++), rr.getOperationCertificate(), style4data);
        }
        BaseFeeExportExcel.setSheetColumnWidthAutoResize(sheet, 17);
        OutputStream out = null;
        try {
            String excelFileName = "人员车辆信息.xls";
            String fileName = "";
            String agent = request.getHeader("USER-AGENT");
            boolean isIE = (null != agent && -1 != agent.indexOf("MSIE") || null != agent
                    && -1 != agent.indexOf("Trident")); //IE
            if (isIE) {
                fileName = java.net.URLEncoder.encode(excelFileName, "UTF8");
            } else {
                fileName = new String(excelFileName.getBytes("UTF-8"), "ISO-8859-1");
            }
            String headStr = "attachment; filename=\"" + fileName + "\"";
            response.setContentType("APPLICATION/OCTET-STREAM");
            response.setHeader("Content-Disposition", headStr);
            out = response.getOutputStream();
            workbook.write(out);
        } catch (IOException e) {
            logger.error("导出失败", e);
        } finally{
            try{
                out.flush();
                out.close();
                workbook.close();
            }catch (IOException e){
                logger.error("IO流close失败!");
            }
        }
    }

    private List<ExportClientCarVo> makeVoFoExport(List<Contract> data) throws BusinessException {
        List<ExportClientCarVo> result = new ArrayList<>();
        if (CollectionUtil.isEmpty(data)){
            return result;
        }
        //获取人员信息
        List<Long> clientIds = data.stream().map(Contract::getUserId).collect(Collectors.toList());
        ServiceResult<List<Client>> clListServiceResult = clientService.getClientByIdIn(clientIds);
        if (!clListServiceResult.getSuccess()){
            throw new BusinessException("获取人员信息报错，错误信息：" + clListServiceResult.getMessage(),ErrorCode.BUSINESS_DEFAULT_ERROR.getCode());
        }
        //获取车辆信息
        List<Long> carIds = data.stream().map(Contract::getCarId).collect(Collectors.toList());
        ServiceResult<List<CarInfo>> carListServiceResult = carInfoService.getCarInfoByIdIn(carIds);
        if (!carListServiceResult.getSuccess()){
            throw new BusinessException("获取车辆信息报错，错误信息：" + carListServiceResult.getMessage(),ErrorCode.BUSINESS_DEFAULT_ERROR.getCode());
        }
        Map<Long,Client> clientMap = clListServiceResult.getBody().stream().collect(Collectors.toMap(Client::getId,obj -> obj));
        Map<Long,CarInfo> carInfoMap = carListServiceResult.getBody().stream().collect(Collectors.toMap(CarInfo::getId,obj -> obj));

        data.forEach(obj -> {
            ExportClientCarVo vo = new ExportClientCarVo();
            vo.setBusNumber(obj.getBusNumber());
            vo.setName(obj.getUserName());
            vo.setCompanyName(obj.getCompanyName());
            vo.setEngineNumber(carInfoMap.get(obj.getCarId()).getEngineNumber());
            vo.setOperationCertificate(carInfoMap.get(obj.getCarId()).getOperationCertificate());
            vo.setVehicleIdentificationNumber(carInfoMap.get(obj.getCarId()).getVehicleIdentificationNumber());
            vo.setAddress(clientMap.get(obj.getUserId()).getAddress());
            vo.setMobile(clientMap.get(obj.getUserId()).getMobile());
            vo.setIdentifyNumber(clientMap.get(obj.getUserId()).getIdentifyNumber());

            result.add(vo);
        });
        return result;
    }

    private void setCellValue(Cell cell,String text,HSSFCellStyle style){
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellStyle(style);
        cell.setCellValue(text);
    }
}

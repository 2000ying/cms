var $ =layui.$;
function postData(url, param, callback) {
    $.post({
        url:url,
        data:param,
        success:function (data) {
            /*解析结果*/
            if(data.success) {
                callback(data);
            } else {
                //异常处理；
                layer.msg( data.message);
            }
        },
        error: function(xhr, type, errorThrown) {
            layer.msg("发生未知错误，请联系技术人员！");
        }
    });
}
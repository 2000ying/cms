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

/**
 * 格式化时间戳
 * @param data 时间戳
 * @param format 格式，比如：'yyyy-MM-dd h:m:s'
 */
function formatTimestamp(data,format) {
    Date.prototype.format = function(format) {
        var date = {
            "M+": this.getMonth() + 1,
            "d+": this.getDate(),
            "h+": this.getHours(),
            "m+": this.getMinutes(),
            "s+": this.getSeconds(),
            "q+": Math.floor((this.getMonth() + 3) / 3),
            "S+": this.getMilliseconds()
        };
        if (/(y+)/i.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
        }
        for (var k in date) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1
                    ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
            }
        }
        return format;
    }
    var newDate = new Date();
    newDate.setTime(data * 1000);
    
    return newDate.format(format);
}
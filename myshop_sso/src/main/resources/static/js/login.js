// 跨域验证是否登录
$(function () {
    $.ajax({
        url:"http://localhost:8084/sso/checklogin",
        method:"post",
        dataType:"jsonp"
    });
})

function loginInfo(data) {
    if (data != null){
        var user = eval("("+data+")")
        $("#login_info").html(" <p>您好，欢迎"+user.username+"来到ShopCZ商城[<a href='http://localhost:8084/sso/signout'>注销</a>]</p>")
    }else{
        $("#login_info").html(" <p>[<a href='javascript:login();'>登录</a>][<a >注册</a>]</p>")
    }
}



function login() {
    var localUrl=location.href;
    //对当前的url进行编码
    localUrl = encodeURI(localUrl);
    //将url中的&转义
    localUrl=localUrl.replace("&","%26");

    location.href="http://localhost:8084/sso/tologin?returnUrl="+localUrl;
}
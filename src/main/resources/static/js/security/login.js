$(function () {
    // 用户首页url
    let toIndexUrl = "/views/user/toIndex";
    // 管理员首页url
    let toAdminIndexUrl = "/views/admin/toCarList";
    // 登录api接口url
    let loginUrl = "/api/v1/security/login/tel";

    $('#submit').click(function () {
        $.ajax({
            async : false,
            url : loginUrl,
            type : 'POST',
            contentType : 'application/x-www-form-urlencoded',
            data : ({
                "tel" : $('#tel').val(),
                "password" : $('#password').val()
            }),
            success : function (data) {
                if (data.code == 200) {
                    window.location.href = (data.data? toAdminIndexUrl: toIndexUrl);
                } else {
                    failHandle(data.code);
                }
            }
        });
    });
});
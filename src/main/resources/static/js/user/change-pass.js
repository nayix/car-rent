$(function(){
    //返回个人信息界面
    let toProfileUrl = "/views/user/toProfile";
    //添加密码接口Url
    let passwordUrl = "/api/v1/users/password";

    //id=back组件点击事件
    $('#back').click(function(){
        window.location.href = toProfileUrl;
    });

    $('#submit').click(function(){
        $.ajax({
            async : false,
            url : passwordUrl,
            type : 'PUT',
            contentType : 'application/x-www-form-urlencoded',
            data : ({
                "oldPass" : $('#old-pass').val(),
                "newPass" : $('#new-pass').val(),
            }),
            success : function (data) {
                if (data.code == 200) {
                    $.alert('修改成功!', function () {
                        window.location.reload();
                    });
                } else {
                    failHandle(data.code);
                }
            }
        });
    });
});
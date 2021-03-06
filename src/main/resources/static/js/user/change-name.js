$(function(){
    //返回个人信息界面
    let toProfileUrl = "/views/user/toProfile";
    //添加用户名接口Url
    let changeNameUrl = "/api/v1/users/username";

    //id=back组件点击事件
    $('#back').click(function(){
        window.location.href = toProfileUrl;
    });

    $('#submit').click(function(){
        $.ajax({
            async : false,
            url : changeNameUrl,
            type : 'PUT',
            contentType : 'application/x-www-form-urlencoded',
            data : ({
                "username" : $('#username').val(),
            }),
            success : function (data) {
                if (data.code == 200) {
                    $.alert('修改成功!', function () {
                        window.location.href = toProfileUrl;
                    });
                } else {
                    failHandle(data.code);
                }
            }
        });
    });
});
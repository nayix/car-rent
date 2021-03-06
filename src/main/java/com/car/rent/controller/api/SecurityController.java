package com.car.rent.controller.api;

import com.car.rent.constant.response.ResultCode;
import com.car.rent.vo.CommonResult;
import com.car.rent.service.UserService;
import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.car.rent.utils.UserUtils.deleteUserFromSubject;
import static com.car.rent.utils.UserUtils.isAdmin;
import static com.car.rent.utils.VerifyUtils.*;

/**
 * @author nayix
 * @date 2020/7/1 13:14
 */
@Api(tags = "安全相关")
@RestController
@RequestMapping(value = "/api/v1/security")
public class SecurityController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "通过手机号和密码进行注册", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "tel", value = "手机号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    @PostMapping("/register/tel")
    private CommonResult<?> registerByTelAndPassword(@RequestParam String username, @RequestParam String tel, @RequestParam String password) {
        usernameVerify(username);
        telVerify(tel);
        passwordVerify(password);
        userService.addUser(username, tel, password);
        return CommonResult.success();
    }

    @ApiOperation(value = "通过手机和密码登录", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel", value = "手机号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    @PostMapping("/login/tel")
    private CommonResult<Boolean> loginByTelAndPassword(@RequestParam String tel, @RequestParam String password) {
        telVerify(tel);
        passwordVerify(password);
        // 获取当前用户
        Subject subject = SecurityUtils.getSubject();
        // 封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(tel, password);
        // 执行登录方法，无异常说明登陆成功
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            // 用户不存在
            return CommonResult.failed(ResultCode.USER_NOT_FOUND);
        } catch (IncorrectCredentialsException e) {
            // 密码错误
            return CommonResult.failed(ResultCode.WRONG_PASS);
        }
        return CommonResult.success(isAdmin());
    }

    @ApiOperation(value = "通过手机和密码注销", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel", value = "手机号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    @PostMapping("/logoff/tel")
    private CommonResult<?> logoffByTelAndPassword(@RequestParam String tel, @RequestParam String password) {
        telVerify(tel);
        passwordVerify(password);
        userService.logoffByTelAndPass(tel, password);
        deleteUserFromSubject();
        return CommonResult.success();
    }

    @ApiOperation(value = "登出", httpMethod = "POST")
    @PostMapping("/logout")
    private CommonResult<?> logout() {
        deleteUserFromSubject();
        return CommonResult.success();
    }
}

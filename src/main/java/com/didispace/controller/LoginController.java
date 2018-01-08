package com.didispace.controller;

import com.didispace.base.AccessToken;
import com.didispace.base.jwt.Audience;
import com.didispace.base.jwt.JwtHelper;
import com.didispace.domain.User;
import config.ResponseCodeCanstants;
import config.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
@Api(value = "login", description = "登陆模块")
@RestController
@RequestMapping(value="/login/user")
@ResponseBody
public class LoginController {
    @Autowired
    com.didispace.service.userService userService;

    @Autowired
    Audience audience;

    @RequestMapping(value = {"/login"} ,method= RequestMethod.GET)
    @ApiOperation(value = "用户登陆",notes = "用户登陆")
    @ApiImplicitParams({
            @ApiImplicitParam( value = "电话号", name = "phoneNumber", required = true, dataType = "Integer",paramType = "query"),
            @ApiImplicitParam( value = "密码", name = "password", required = true, dataType = "String",paramType = "query")
    })
    @ResponseBody
    public ResponseResult Login(@ApiIgnore User user){
        User user1 = userService.getByPhoneNumber(user.getPhoneNumber());
        if(user1 !=null){
            if(!user.getPassword().equals(user1.getPassword())){
                return new ResponseResult(ResponseCodeCanstants.FAILED,"密码错误");
            }
        }else{
            return new ResponseResult(ResponseCodeCanstants.FAILED,"该账号不存在");
        }
        user1.setLastLoginTime(new Date());
        userService.save(user1);
        String accessToken = JwtHelper.createJWT(user1.getName()
                , user1.getId().toString()
                , null, audience.getClientId()
                , audience.getName()
                , audience.getExpiresSecond() * 1000
                , audience.getBase64Secret());
        AccessToken accessTokenEntity = new AccessToken();
        accessTokenEntity.setUserId(user1.getId());
        accessTokenEntity.setName(user1.getName());
        accessTokenEntity.setAccess_token(accessToken);
        accessTokenEntity.setExpires_in(audience.getExpiresSecond());
        accessTokenEntity.setToken_type("bearer");
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,accessTokenEntity,"success");
    }
}

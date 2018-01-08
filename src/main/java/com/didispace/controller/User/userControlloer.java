package com.didispace.controller.User;


import com.didispace.base.jwt.Audience;
import com.didispace.domain.User;
import com.didispace.service.userService;
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

import java.util.*;

@Api(value = "user", description = "user模块")
@RestController
@RequestMapping(value="/api/user")
@ResponseBody
public class userControlloer {
    @Autowired
    userService userService;

    @Autowired
    Audience audience;


    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());

    @RequestMapping(value={"/findAll"}, method= RequestMethod.GET)
    @ApiOperation(value="获取用户列表", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Token验证码", name = "Authorization", paramType = "header", required = true)
    })
    public ResponseResult getUserList() {
        List<User> r = new ArrayList<User>();
        r=userService.findAll();
        System.out.print(r);
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,r,"success");
    }

    @RequestMapping(value={"/save"}, method= RequestMethod.GET)
    @ApiOperation(value="更新用户", notes="更新用户")
    @ApiImplicitParams({
            @ApiImplicitParam( value = "主键", name = "id", required = false, dataType = "Integer",paramType = "query"),
            @ApiImplicitParam( value = "名称", name = "name",  required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam( value = "年龄", name = "age", required = true, dataType = "Integer",paramType = "query"),
            @ApiImplicitParam( value = "电话", name = "phoneNumber", required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam( value = "密码", name = "password", required = true, dataType = "Integer",paramType = "query")
    })
    @ResponseBody
    public ResponseResult save(@ApiIgnore User user){

        List<User> userList = new ArrayList<User>();
     if("" .equals(user.getId() ) || null != user.getId()){
         user.setUpdateTime(new Date());
         userService.save(user);
     }else{
         User user1 = userService.getByPhoneNumber(user.getPhoneNumber());
         if(user1 ==null ){
             userService.save(user);
         } else{
             return new ResponseResult(ResponseCodeCanstants.FAILED,"该手机号已经注册");
         }
     }
        userList = userService.findAll();
        return new ResponseResult(ResponseCodeCanstants.SUCCESS,userList,"success");
    }

}

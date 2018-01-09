package com.didispace.controller.mail;


import config.ResponseCodeCanstants;
import config.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "mail", description = "mail模块")
@RestController
@RequestMapping(value="/api/mail")
public class mailController {
    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping(value={"/sendMail"}, method= RequestMethod.GET)
    @ApiOperation(value="发送邮件", notes="发送邮件")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Token验证码", name = "Authorization", paramType = "header", required = true),
            @ApiImplicitParam(value = "邮件地址", name = "mailAddress",  required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(value = "邮件主题", name = "mailSubject",  required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(value = "邮件内容", name = "mailText",     required = true, dataType = "String",paramType = "query"),
    })
public ResponseResult sendMail(String mailAddress,String mailSubject,String mailText){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(ResponseCodeCanstants.Mail_from);
        message.setTo(mailAddress);
        message.setSubject(mailSubject);
        message.setText(mailText);
       try{ mailSender.send(message);
       }catch (Exception e){
           e.printStackTrace();
           return new ResponseResult(ResponseCodeCanstants.FAILED,"发送失败");
       }
    return new ResponseResult(ResponseCodeCanstants.SUCCESS,"发送成功");
}

}

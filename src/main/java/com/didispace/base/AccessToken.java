package com.didispace.base;

import io.swagger.annotations.ApiModelProperty;

public class AccessToken {

    @ApiModelProperty(value = "用户ID")
    private Integer id;
    @ApiModelProperty(value = "用户名")
    private String name;
    @ApiModelProperty(value = "Token")
    private String access_token;
    @ApiModelProperty(value = "Token类型")
    private String token_type;
    @ApiModelProperty(value = "超时时间")
    private long expires_in;

//    @ApiModelProperty(value = "角色ID")
//    private String roleid;
//    @ApiModelProperty(value = "权重")
//    private String weight;

//    public String getWeight() {
//        return weight;
//    }
//
//    public void setWeight(String weight) {
//        this.weight = weight;
//    }
//
//    public String getRoleid() {
//        return roleid;
//    }
//
//    public void setRoleid(String roleid) {
//        this.roleid = roleid;
//    }

    public Integer getUserId() {
        return id;
    }

    public void setUserId(Integer id) {
        this.id = id;
    }
    public String getAccess_token() {
        return access_token;
    }
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    public String getToken_type() {
        return token_type;
    }
    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
    public long getExpires_in() {
        return expires_in;
    }
    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
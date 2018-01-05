package com.didispace.domain;

import JUtils.encrypt.EncryptAndDecryptUtils;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author 程序猿DD
 * @version 1.0.0
 * @date 16/3/21 下午3:35.
 * @blog http://blog.didispace.com
 */

@Data
@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private Integer age;

    @Column(nullable = true)
    private String password;

    @Column(nullable = true)
    private String phoneNumber;

    @Column(nullable = true)
    private String  sex;

    @Column(nullable = true)
    private Date  createTime;

    @Column(nullable = true)
    private Date  updateTime;

    @Column(nullable = true)
    private Date  lastLoginTime;

    @Column(nullable = true)
    private char  del_flag;

    public User() {
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setPassword(String password) {
        this.password = EncryptAndDecryptUtils.md5(password) ;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public void setDel_flag(char del_flag) {
        this.del_flag = del_flag;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getPassword() {
        return EncryptAndDecryptUtils.md5Encrypt(password);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSex() {
        return sex;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public char getDel_flag() {
        return del_flag;
    }
}

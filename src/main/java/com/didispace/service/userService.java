package com.didispace.service;

import com.didispace.domain.User;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface userService    {
    List<User> findAll();
    void save(User user);
    List<User> findList();
    User getOne(User user);
    User getByPhoneNumber(String phoneNumber);
}

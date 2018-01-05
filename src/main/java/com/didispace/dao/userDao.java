package com.didispace.dao;

import com.didispace.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface  userDao  extends JpaRepository<User, Integer> {
    @Query("select  t from  User   t ")
    List<User>findList();

    @Query("select t from User  t where t.phoneNumber = ?1")
    User getByPhoneNumber(String phoneNumber);
}

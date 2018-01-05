package com.didispace.service.userServiceImpl;


import com.didispace.dao.userDao;
import com.didispace.domain.User;
import com.didispace.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class userServiceimpl implements userService {
    @Autowired
    userDao dao;

    public List<User>  findAll(){
        return  dao.findAll();
    }

    public void save(User user) {
        dao.save(user);
    }

    public List<User>findList(){
        return dao.findList();
    }

    public User getOne(User user){
        return dao.getOne(user.getId());
    }

    public User getByPhoneNumber(String phoneNumber){
        return dao.getByPhoneNumber(phoneNumber);
    }
}

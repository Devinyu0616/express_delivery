package com.devin.web.service;

import com.devin.web.bean.User;

public interface UserService {
    //注册
    boolean addUser(User user) throws Exception;

    //修改密码
    boolean updateUser(String userId,String oldPassword,String newPassword) throws Exception;

    //用户登录
    User loginUser(User u) throws Exception;

    //检测用户名是否存在
    boolean exitUserName(String userName) throws Exception;
}

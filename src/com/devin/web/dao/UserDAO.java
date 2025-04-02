package com.devin.web.dao;

import com.devin.web.bean.User;

public interface UserDAO {
    //添加新用户
    int addUser(User user)throws Exception;
    //修改密码
    int updatePassword(String username, String oldPasswd,String newPassword)throws Exception;
    //检查用户名是否存在
    boolean checkUsername(String username)throws Exception;
    //根据用户名和密码查询用户对象
   User queryUser(String username, String password)throws Exception;
}

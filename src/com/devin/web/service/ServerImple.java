package com.devin.web.service;

import com.devin.web.bean.User;
import com.devin.web.dao.BaseDAOPlus;
import com.devin.web.dao.JDBCToolsPlus;
import com.devin.web.dao.UserDAOImple;

public class ServerImple implements UserService{
    private final UserDAOImple userDAO = new UserDAOImple();
    @Override   //检测用户名是否存在
    public boolean exitUserName(String userName) throws Exception {
        boolean f = userDAO.checkUsername(userName);
        JDBCToolsPlus.freeConnection();
        return f;
    }

    @Override
    public User loginUser(User u) throws Exception {
        User uq= userDAO.queryUser(u.getUsername(), u.getPassword());
        JDBCToolsPlus.freeConnection();

        return uq;
    }

    @Override
    public boolean updateUser(String userName, String oldPassword, String newPassword) throws Exception {
        // TODO
        int b = userDAO.updatePassword(userName, oldPassword, newPassword);
        JDBCToolsPlus.freeConnection();

        return b > 0;
    }

    @Override
    public boolean addUser(User user) throws Exception {
        int b = userDAO.addUser(user);
        JDBCToolsPlus.freeConnection();
        return b > 0;
    }
}

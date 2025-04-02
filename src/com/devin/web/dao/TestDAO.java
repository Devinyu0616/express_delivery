package com.devin.web.dao;

import com.devin.web.bean.User;

public class TestDAO {
    public static void main(String[] args) throws Exception {
        UserDAOImple userDAOImple = new UserDAOImple();
//        boolean flag = userDAOImple.checkUsername("roo11t123");
//        System.out.println(flag);
//        userDAOImple.addUser(new User("Devin","admin","德智"));
//        String sql = "update sys_user set password = md5(?) where username = ?";
//        BaseDAOPlus.update(sql,"Devin","admin");
        String sql = "update sys_user set password = ? where username = ?";
        int flag = BaseDAOPlus.update(sql,"Devinyu12","Devinyu123");
    }
}

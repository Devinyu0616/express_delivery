package com.devin.web.dao;

import com.devin.web.bean.User;

import java.util.List;

public class TestBaseDao {
    public static void main(String[] args) throws Exception {
        List<User> list = BaseDAOPlus.getList(User.class, "select * from sys_user");
        for (User user : list) {
            System.out.println(user);
        }

    }
}

package com.devin.web.dao;

import com.devin.web.bean.User;


import java.sql.SQLException;
import java.util.List;

public class UserDAOImple implements UserDAO {
    @Override
    public int addUser(User user) throws Exception {
//        BaseDAOPlus
//        public static int update(String sql, Object... args)throws SQLException {
//        private String username;
//        private String password;
//        private String nickname;
        String sql = "insert into sys_user (username,password,nickname)values(?,md5(?),?)";
        int flag = BaseDAOPlus.update(sql,user.getUsername(),user.getPassword(),user.getNickname());
        return flag >0?1:0;
    }
    //更新密码
    @Override
    public int updatePassword(String username, String oldPasswd,String newPassword) throws Exception {
        //        BaseDAOPlus
//        public static int update(String sql, Object... args)throws SQLException {
//        private String username;
//        private String password;
//        private String nickname;
        // >>>TODO 检查旧密码是否正确
        // >>> TODO 如果旧密码正确则进行修改
        String sql = "update sys_user set password = md5(?) where username = ?";
        int flag = BaseDAOPlus.update(sql,newPassword,username);
        return flag >0?1:0;
    }

    @Override
    public boolean checkUsername(String username) throws Exception {
        // public static Object getValue(String sql, Object... args)throws Exception{
        String sql = "select * from sys_user where username = ?";
        Object idValue = BaseDAOPlus.getValue(sql, username);
        //有结果返回真
        return idValue!=null;
    }


    @Override
    public User queryUser(String username, String password) throws Exception {
        String sql = "select * from sys_user where username = ? and password = md5(?)";
        List<User> list = BaseDAOPlus.getList(User.class, sql,username,password);
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }
}

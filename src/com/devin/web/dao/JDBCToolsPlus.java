package com.devin.web.dao;


import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCToolsPlus {
    private static DataSource dataSource; //比喻：图书馆
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();//threadLocal比喻成书包
    static {
        try {
            //静态代码块。在类初始化的时候执行，每一个类只会执行一次。
            Properties properties = new Properties();
            //放在src下的，会被编译到classpath中，可以通过类加载器加载
            properties.load(JDBCToolsPlus.class.getClassLoader().getResourceAsStream("jdbc.properties"));
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //上面的静态代码块，就是创建了一个图书馆。

    public static Connection getConnection()throws SQLException {
        Connection connection = threadLocal.get(); //翻看自己的书包，看有没有 书
        if(connection == null){//书包里没有你要的书
            connection = dataSource.getConnection();//去图书馆 借一本书
            threadLocal.set(connection);//把这本书装到书包中。
        }
        return connection;//返回书
    }
    public static void freeConnection()throws SQLException{
        Connection connection = threadLocal.get();//翻看自己的书包，看有没有 书
        if(connection != null){//如果书包中有 你要还的书
            threadLocal.remove(); //从书包中拿走
            connection.setAutoCommit(true);//设置默认自动提交事务
            connection.close();//还给图书馆
        }
    }
}

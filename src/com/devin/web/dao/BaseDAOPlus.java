package com.devin.web.dao;


import com.devin.web.dao.JDBCToolsPlus;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BaseDAOPlus {
    /*
    *@param sql
    *
    *
    * */
    public static int update(String sql, Object... args)throws SQLException {
        Connection connection = JDBCToolsPlus.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //设置？
        if(args != null && args.length>0){
            for(int i=0; i<args.length; i++){
                //在Java中数组的下标是从[0]开始，但是MySQL操作，第几个?是从1开始
                preparedStatement.setObject(i+1, args[i]);
            }
        }

        int len = preparedStatement.executeUpdate();
//        preparedStatement.close();
        //这里不能关闭，关闭连接就被还给连接池了，再调用update方法就变成新的连接了
        return len;
    }

    //通用的查询一组对象的方法
    public static <T> List<T> getList(Class<T> clazz, String sql, Object... args)throws Exception{
        Connection connection = JDBCToolsPlus.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //设置？
        if(args != null && args.length>0){
            for(int i=0; i<args.length; i++){
                //在Java中数组的下标是从[0]开始，但是MySQL操作，第几个?是从1开始
                preparedStatement.setObject(i+1, args[i]);
            }
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();//获取结果集的元数据
        int columnCount = metaData.getColumnCount();//获取列数

        List<T> list = new ArrayList<>();//创建集合，准备用来装一组对象

        //遍历结果集
        while(resultSet.next()){//while循环循环一次，代表一行
            //表中的一行，对应Java的一个对象
            //创建一个T类型的Java对象
            Constructor<T> noArgsConstructor = clazz.getDeclaredConstructor();
            //提醒大家：以后写Javabean都要保留无参构造
            noArgsConstructor.setAccessible(true);//如果构造器是公共的，这句代码可以省略
            T t = noArgsConstructor.newInstance();

            //从结果集中，读取一个一个的单元格的数据，为t对象的一个一个属性赋值
            for(int i=1; i<=columnCount; i++){//for循环循环一次，代表一行中的一个单元格
                Object value = resultSet.getObject(i);
                //给t对象设置属性
                //获取t对象的某个Field属性
//                String columnName = metaData.getColumnName(i);//获取列的名称
                String columnName = metaData.getColumnLabel(i);//获取列的名称或别名
                Field field = clazz.getDeclaredField(columnName);//列的名称对应的就是Javabean的属性名
                field.setAccessible(true);//属性通常都是私有化的，所以需要setAccessible(true)
                field.set(t, value);
            }

            list.add(t);//把对象添加的集合中
        }
        preparedStatement.close();
        return list;
    }

    //查询单个值的通用方法
    public static Object getValue(String sql, Object... args)throws Exception{
        Connection connection = JDBCToolsPlus.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //设置？
        if(args != null && args.length>0){
            for(int i=0; i<args.length; i++){
                //在Java中数组的下标是从[0]开始，但是MySQL操作，第几个?是从1开始
                preparedStatement.setObject(i+1, args[i]);
            }
        }

        ResultSet resultSet = preparedStatement.executeQuery();

        Object value = null;
        if(resultSet.next()){
            value = resultSet.getObject(1);
        }
        preparedStatement.close();
        return value;
    }

    public static Map<Object,Object> getMap(String sql, Object... args)throws Exception{
        Connection connection = JDBCToolsPlus.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //设置？
        if(args != null && args.length>0){
            for(int i=0; i<args.length; i++){
                //在Java中数组的下标是从[0]开始，但是MySQL操作，第几个?是从1开始
                preparedStatement.setObject(i+1, args[i]);
            }
        }

        ResultSet resultSet = preparedStatement.executeQuery();

        Map<Object,Object> map = new HashMap<>();
        while(resultSet.next()){
            map.put(resultSet.getObject(1),resultSet.getObject(2));
        }

        preparedStatement.close();
        return map;

    }
}

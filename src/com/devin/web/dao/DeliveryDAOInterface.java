package com.devin.web.dao;

import com.devin.web.bean.Delivery;

import java.util.List;

public interface DeliveryDAOInterface {
    long count(Integer userId)throws Exception;
    //完成添加新的快递记录
    int addDelivery(Delivery delivery)throws Exception;

    //删除快递记录
    int removeDelivery(Integer deliveryId)throws Exception;

    //修改快递记录
    int updateDelivery(Delivery delivery)throws Exception;

    //在修改之前，根据id查询快递记录的详细信息
    Delivery queryById(Integer deliveryId)throws Exception;

    //更新快递记录
    int updateById(Delivery delivery)  throws Exception;

    //根据userId查询该用户发的所有快递记录
    List<Delivery> queryList(Integer userId)throws Exception;

    List<Delivery> queryByUserId(Integer userId) throws Exception;

    //分页查询当前登录用户对应的所有快递记录
    List<Delivery> queryByUserId(Integer userId, int page, int pageCount) throws Exception;
}

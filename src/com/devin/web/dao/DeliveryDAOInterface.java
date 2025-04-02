package com.devin.web.dao;

import com.devin.web.bean.Delivery;

import java.util.List;

public interface DeliveryDAOInterface {
    //完成添加新的快递记录
    int addDelivery(Delivery delivery)throws Exception;

    //删除快递记录
    int removeDelivery(Integer deliveryId)throws Exception;

    //修改快递记录
    int updateDelivery(Delivery delivery)throws Exception;

    //在修改之前，根据id查询快递记录的详细信息
    Delivery queryById(Integer deliveryId)throws Exception;

    //根据userId查询该用户发的所有快递记录
    List<Delivery> queryList(Integer userId)throws Exception;

}

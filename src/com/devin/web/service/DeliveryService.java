package com.devin.web.service;

import com.devin.web.bean.Delivery;

import java.util.List;

public interface DeliveryService {
List<Delivery> findList(Integer userId)throws Exception;

Delivery getDetail(String id)throws Exception;
//保存快递记录更新到数据库
boolean saveDelivery(Delivery delivery)throws Exception;
boolean deleteDelivery(Integer deliveryId)throws Exception;
boolean updateDelivery(Delivery delivery)throws Exception;
Delivery findDeliveryById(Integer deliveryId)throws Exception;
List<Delivery> getAllDeliveryByUserId(Integer userId)throws Exception;
//查询当前登录用户对应的所有快递的记录总数
long getTotal(Integer userId)throws Exception;

    //分页查询当前登录用户的所有快递记录
    List<Delivery> findList(Integer userId, int page, int pageCount) throws Exception;
}

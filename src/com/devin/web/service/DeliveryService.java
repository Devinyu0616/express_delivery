package com.devin.web.service;

import com.devin.web.bean.Delivery;

import java.util.List;

public interface DeliveryService {
    //保存快递记录更新到数据库
boolean saveDelivery(Delivery delivery)throws Exception;
boolean deleteDelivery(Integer deliveryId)throws Exception;
boolean updateDelivery(Delivery delivery)throws Exception;
Delivery findDeliveryById(Integer deliveryId)throws Exception;
List<Delivery> getAllDeliveryByUserId(Integer userId)throws Exception;
}

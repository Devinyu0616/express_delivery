package com.devin.web.service;

import com.devin.web.bean.Delivery;
import com.devin.web.dao.*;
import java.util.List;


public class DeliveryServiceImpl implements DeliveryService{
    private final DeliveryDAOImpl deliveryDAO = new DeliveryDAOImpl();

    //保存新添加的快递记录
    @Override
    public boolean saveDelivery(Delivery delivery) throws Exception {
        int len = deliveryDAO.addDelivery(delivery);
        JDBCToolsPlus.freeConnection();
        return len>0;
    }

    //删除快递记录
    //这里的deliveryId可以是Integer类型，也可以是String类型
    @Override
    public boolean deleteDelivery(Integer deliveryId) throws Exception {
        int len = deliveryDAO.removeDelivery(deliveryId);
        JDBCToolsPlus.freeConnection();
        return len>0;
    }

    //修改快递记录
    @Override
    public boolean updateDelivery(Delivery delivery) throws Exception {
        int len = deliveryDAO.updateDelivery(delivery);
        JDBCToolsPlus.freeConnection();
        return len>0;
    }

    //在修改之前，根据id查询快递记录的详细信息
    @Override
    public Delivery findDeliveryById(Integer deliveryId) throws Exception {
        Delivery Delivery = deliveryDAO.queryById(deliveryId);
        JDBCToolsPlus.freeConnection();
        return Delivery;
    }
    @Override
    public Delivery getDetail(String id) throws Exception {
        Delivery delivery = deliveryDAO.queryById(Integer.parseInt(id));
        JDBCToolsPlus.freeConnection();
        return delivery;
    }
    @Override
    public long getTotal(Integer userId) throws Exception {
        long count = deliveryDAO.count(userId);
        JDBCToolsPlus.freeConnection();
        return count;
    }

    //根据userId查询该用户发的所有快递记录
    @Override
    public List<Delivery> getAllDeliveryByUserId(Integer userId) throws Exception {
        List<Delivery> list = deliveryDAO.queryList(userId);
        JDBCToolsPlus.freeConnection();
        return list;
    }
    //查询当前登录用户的所有快递记录
    @Override
    public List<Delivery> findList(Integer userId) throws Exception {
        List<Delivery> list = deliveryDAO.queryByUserId(userId);
        JDBCToolsPlus.freeConnection();
        return list;
    }
    //分页查询当前登录用户的所有快递记录
    @Override
    public List<Delivery> findList(Integer userId, int page, int pageCount) throws Exception {
        List<Delivery> list = deliveryDAO.queryByUserId(userId, page, pageCount);
        JDBCToolsPlus.freeConnection();
        return list;
    }

}

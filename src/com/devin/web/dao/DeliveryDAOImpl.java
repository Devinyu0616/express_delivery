package com.devin.web.dao;

import com.devin.web.bean.Delivery;

import java.util.List;

public class DeliveryDAOImpl implements DeliveryDAOInterface{
    @Override
    public int addDelivery(Delivery delivery) throws Exception {
        String sql = "insert into delivery (delivery_name, user_id, company_id, phone, " +
                                                 "address, send_time,state) values(?,?,?,?,?,?,?)";
        return BaseDAOPlus.update(sql,
                delivery.getDeliveryName(),
                delivery.getUserId(),
                delivery.getCompanyId(),
                delivery.getPhone(),
                delivery.getSendTime(),
                delivery.getState()
        );
    }

    @Override
    public int removeDelivery(Integer deliveryId) throws Exception {
        String sql = "delete from sys_delivery where id = ?";

        return BaseDAOPlus.update(sql, deliveryId);
    }

    @Override
    public int updateDelivery(Delivery delivery) throws Exception {
        String sql = "update sys_delivery set delivery_name=?, user_id=?, company_id=?, phone=?, address=?, send_time=?,state=? where id=?";
        return BaseDAOPlus.update(sql,delivery.getDeliveryName(),
                delivery.getUserId(),
                delivery.getCompanyId(),
                delivery.getPhone(),
                delivery.getAddress(),
                delivery.getSendTime(),
                delivery.getState(),
                delivery.getId());
    }

    @Override
    public Delivery queryById(Integer deliveryId) throws Exception {
        String sql = "select id,delivery_name deliveryName, user_id userId, company_id companyId, phone, address, send_time sendTime,state from sys_delivery where id=?";
        List<Delivery> list = BaseDAOPlus.getList(Delivery.class, sql, deliveryId);
        return !list.isEmpty()? list.get(0):null;
    }

    @Override
    public List<Delivery> queryList(Integer userId) throws Exception {
        String sql = "select id,delivery_name deliveryName, user_id userId, company_id companyId, phone, address, send_time sendTime,state from sys_delivery  where user_id=?";
        return BaseDAOPlus.getList(Delivery.class, sql, userId);
    }
}

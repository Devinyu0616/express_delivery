package com.devin.web.dao;

import com.devin.web.bean.Delivery;

import java.util.List;

public class DeliveryDAOImpl implements DeliveryDAOInterface{
    @Override
    public int addDelivery(Delivery delivery) throws Exception {
        String sql = "insert into sys_delivery (delivery_name, user_id, company_id, phone,address, send_time,state) values(?,?,?,?,?,?,?)";
        return BaseDAOPlus.update(sql,
                delivery.getDeliveryName(),
                delivery.getUserId(),
                delivery.getCompanyId(),
                delivery.getPhone(),
                delivery.getAddress(),
                delivery.getSendTime(),
                delivery.getState()
        );
    }
    @Override
    public long count(Integer userId)throws Exception{
        String sql = "select count(*) from sys_delivery where user_id=?";
        return (long) BaseDAOPlus.getValue(sql,userId);
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

    //更新快递记录
    @Override
    public int updateById(Delivery delivery)  throws Exception{
        String sql = "update sys_delivery set delivery_name = ?,user_id =?, company_id = ? , phone = ? , address = ?," +
                "send_time = ? , state = ? where id = ?";
        return BaseDAOPlus.update(sql, delivery.getDeliveryName(), delivery.getUserId(), delivery.getCompanyId(),
                delivery.getPhone(), delivery.getAddress(), delivery.getSendTime(), delivery.getState(),delivery.getId());
    }
    @Override
    public List<Delivery> queryList(Integer userId) throws Exception {
        String sql = "select id,delivery_name deliveryName, user_id userId, company_id companyId, phone, address, send_time sendTime,state from sys_delivery  where user_id=?";
        return BaseDAOPlus.getList(Delivery.class, sql, userId);
    }
    @Override
    public List<Delivery> queryByUserId(Integer userId) throws Exception{
        String sql = "select id , delivery_name deliveryName , user_id userId , company_id companyId , " +
                "phone , address , state ,send_time sendTime from sys_delivery where user_id = ? ;";
        return BaseDAOPlus.getList(Delivery.class,sql,userId);
    }

    //分页查询当前登录用户对应的所有快递记录
    @Override
    public List<Delivery> queryByUserId(Integer userId, int page, int pageCount) throws Exception {
        String sql = "select id , delivery_name deliveryName , user_id userId , company_id companyId , " +
                "phone , address , state ,send_time sendTime from sys_delivery where user_id = ? limit ?,?";
        return BaseDAOPlus.getList(Delivery.class,sql,userId,(page-1)*pageCount,pageCount);
    }
}

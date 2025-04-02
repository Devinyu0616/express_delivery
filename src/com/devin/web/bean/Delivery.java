package com.devin.web.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {
    private int id;
    private String deliveryName;
    private int userId;
    private int companyId;
    private String phone;
    private String address;
    private Date sendTime;
    private int state;

    private Company company; //关联公司对象
}

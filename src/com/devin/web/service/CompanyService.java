package com.devin.web.service;

import com.devin.web.bean.Company;

import java.util.List;

public interface CompanyService {
    //查询所有快递公司
    List<Company> getAllCompany()throws Exception;
}

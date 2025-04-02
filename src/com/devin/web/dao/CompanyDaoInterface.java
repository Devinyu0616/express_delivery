package com.devin.web.dao;

import com.devin.web.bean.Company;

import java.util.List;

public interface CompanyDaoInterface {
        //查询所有快递公司
        List<Company> queryList() throws Exception;
}

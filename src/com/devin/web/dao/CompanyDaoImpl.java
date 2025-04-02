package com.devin.web.dao;

import com.devin.web.bean.Company;

import java.util.List;

public class CompanyDaoImpl implements CompanyDaoInterface{
    @Override
    public List<Company> queryList() throws Exception {
        //sql中通过给字段取别名的方式，来解决Javabean的属性名与表中的字段名不完全对应的问题
        String sql = "select id, company_name companyName from sys_company";
        return BaseDAOPlus.getList(Company.class, sql);
    }
}

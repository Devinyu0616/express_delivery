package com.devin.web.service;

import com.devin.web.bean.Company;
import com.devin.web.dao.CompanyDaoImpl;
import com.devin.web.dao.CompanyDaoInterface;
import com.devin.web.dao.JDBCToolsPlus;

import java.util.List;

public class CompanyServiceImpl implements CompanyService{
    private final CompanyDaoInterface companyDAO = new CompanyDaoImpl();
    @Override
    public List<Company> getAllCompany() throws Exception {
        List<Company> list = companyDAO.queryList();
        JDBCToolsPlus.freeConnection();
        return list;
    }
}

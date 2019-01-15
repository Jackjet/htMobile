package com.kwchina.core.base.dao.impl;

import com.kwchina.core.base.dao.DepartmentDAO;
import com.kwchina.core.base.entity.Department;
import com.kwchina.core.common.dao.BasicDaoImpl;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentDAOImpl extends BasicDaoImpl<Department> implements DepartmentDAO {
//	public Integer getNewDepartmentId() {
//
//		String sql = "select max(departmentId) from x_core_department order by departmentId desc";
//		List list = this.getResultBySQLQuery(sql);
//		
//		return list != null ? (Integer.parseInt(list.get(0).toString()) + 1) : 1;
//	}
}
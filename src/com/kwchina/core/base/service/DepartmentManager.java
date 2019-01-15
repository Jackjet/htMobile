package com.kwchina.core.base.service;

import com.kwchina.core.base.entity.Department;
import com.kwchina.core.base.vo.DepartmentVo;
import com.kwchina.core.common.service.BasicManager;

import java.util.ArrayList;
import java.util.List;


public interface DepartmentManager extends BasicManager{
//	public List<DepartmentDTO> getDepartmentDTO();
	//获取部门信息
	public List<Department> getDepartments();
	//获取所有部门
	public List<Department> getAllDepartments();
	//获取动检部门
	public List<Department> getUnionDepartments();

	//根据名字获取部门
	public Department getDepartmentByName(String departmentName);
	
	//获取班组信息
	public List<DepartmentVo> getGroups(int levelId);
	
	//获取未删除的所有组织结构信息
	public List getUndeleted();
	
	/**
	 * 按照树状结构组织DepartmentInfor信息 
	 * @param
	 * 根分类Id
	 */
	public ArrayList getDepartmentAsTree(Integer xId);
	
//	//获取指定用户的部门信息
//	public List getDepFromUsers(List users);
	
	public Department getDepartment(Integer xId);
	public Department getDepartmentByDeparmentId(Integer departmentId);
	public void disable(Department department);
	public DepartmentVo getVoById(Integer departmentId);

	public List<Department> findAllChildren(Department department);
}

package com.kwchina.core.base.dto;

import java.util.Collection;
import java.util.LinkedHashSet;

import com.kwchina.core.base.entity.Department;

public class DepartmentDTO {
	
	private String id;
	private String text;
	private String attributes;
	private Collection<DepartmentDTO> children = new LinkedHashSet<DepartmentDTO>();
	
	public DepartmentDTO() {
		
	}
	
	public DepartmentDTO(Department root) {
		copy(root);
	}
	
	public DepartmentDTO(Department root,String undertake) {
		copy(root,undertake);
	}

	public DepartmentDTO(Department root, Collection<Department> departments) {
		setId(root.getxId().toString());
		setText(root.getDepartmentName());
		setAttributes("{\"departmentId\":\"" + root.getDepartmentId() + "\"}");
		
		copy(root, null, departments);
	}
	
	public DepartmentDTO(Department root, Collection<Department> departments,String undertake) {
		setId(root.getxId().toString());
		setText(root.getDepartmentName());
		setAttributes("{\"departmentId\":\"" + root.getDepartmentId() + "\"}");
		
		copy(root, null, departments,undertake);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public Collection<DepartmentDTO> getChildren() {
		return children;
	}

	public void setChildren(Collection<DepartmentDTO> children) {
		this.children = children;
	}
	
	public void addChild(DepartmentDTO departmentDTO)
	{
		this.children.add(departmentDTO);
	}
	
	public void addChild(DepartmentDTO parentDTO, DepartmentDTO departmentDTO)
	{
		parentDTO.children.add(departmentDTO);
	}
	
	public void removeChild(DepartmentDTO departmentDTO)
	{
		this.children.remove(departmentDTO);
	}
	
	/*
	 * Copy all departments into tree
	 */
	public void copy(Department department)
	{
		setId(department.getxId().toString());
		setText(department.getDepartmentName());
		setAttributes("{\"departmentId\":\"" + department.getDepartmentId() + "\"}");
		for(Department child : department.getChildren()) {
			// TODO: using filter instead
			if (child.isValid()) {
				DepartmentDTO departmentDTO = new DepartmentDTO(child);
				addChild(departmentDTO);
			}
		}
	}
	
	/*
	 * Copy all departments into tree
	 */
	public void copy(Department department,String undertake)
	{
		setId(department.getxId().toString());
		setText(department.getDepartmentName());
		setAttributes("{\"departmentId\":\"" + department.getDepartmentId() + "\"}");
		for(Department child : department.getChildren()) {
			// TODO: using filter instead
			if (child.isValid()) {
				DepartmentDTO departmentDTO = new DepartmentDTO(child,undertake);
				addChild(departmentDTO);
			}
		}
	}

	/*
	 * Only copy department in mapping into tree
	 */
	public void copy(Department department, DepartmentDTO parentDTO, Collection<Department> departments)
	{
		if (null == parentDTO)
			parentDTO = this;
		
		for(Department child : department.getChildren()) {
			// TODO: using filter instead
			if (child.isValid()) {
				if (isInclude(child, departments)) {
					DepartmentDTO departmentDTO = new DepartmentDTO(child, departments);
					addChild(parentDTO, departmentDTO);
				}
				else {
					copy(child, parentDTO, departments);
				}
			}
		}
	}
	
	/*
	 * Only copy department in mapping into tree
	 */
	public void copy(Department department, DepartmentDTO parentDTO, Collection<Department> departments,String undertake)
	{
		if (null == parentDTO)
			parentDTO = this;
		
		for(Department child : department.getChildren()) {
				// TODO: using filter instead
			if (child.isValid()) {
				if (isInclude(child, departments)) {
					DepartmentDTO departmentDTO = new DepartmentDTO(child, departments,undertake);
					addChild(parentDTO, departmentDTO);
				}
				else {
					copy(child, parentDTO, departments,undertake);
				}
			}
		}
	}
	
	private boolean isInclude(Department department, Collection<Department> departments) {
		
		for (Department u : departments) {
			if (u.getxId().equals(department.getxId()))
				return true;
		}
		
		return false;
	}

}


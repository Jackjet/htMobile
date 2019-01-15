package com.kwchina.core.base.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kwchina.core.base.vo.DepartmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kwchina.core.base.dao.DepartmentDAO;
import com.kwchina.core.base.entity.Department;
import com.kwchina.core.base.service.DepartmentManager;
import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.core.sys.CoreConstant;

@Service("departmentManager")
public class DepartmentManagerImpl extends BasicManagerImpl<Department> implements DepartmentManager {

    private DepartmentDAO departmentDAO;

    @Autowired
    public void setDepartmentDAO(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
        super.setDao(departmentDAO);
    }


    //获取部门信息
    public List<Department> getDepartments() {
        List<Department> returnLs = new ArrayList<Department>();

        List allDepartment = this.departmentDAO.getAll();
        for (Iterator it = allDepartment.iterator(); it.hasNext(); ) {
            Department department = (Department) it.next();
            if (department.getLevelId() == CoreConstant.Core_Department_Level_Department && department.isValid() && department.getType() == 1) {
                returnLs.add(department);
            }
        }


        return returnLs;
    }

    @Override
    public List<Department> getAllDepartments() {
        List<Department> returnLs = new ArrayList<Department>();

        List allDepartment = this.departmentDAO.getAll();
        for (Iterator it = allDepartment.iterator(); it.hasNext(); ) {
            Department department = (Department) it.next();
            if (department.isValid()) {
                returnLs.add(department);
            }
        }
        return returnLs;
    }

    @Override
    public List<Department> getUnionDepartments() {
        List<Department> returnLs = new ArrayList<>();
        List allDepartment = this.departmentDAO.getAll();
        for (Iterator it = allDepartment.iterator(); it.hasNext(); ) {
            Department department = (Department) it.next();
            if (department.isValid()&&department.getLevelId() == CoreConstant.Core_Department_Level_Department&& department.getType() == 1) {
                returnLs.add(department);
            }
            if(department.getLayer()==0&&department.getDepartmentId()!=1){
                returnLs.add(department);
            }
        }
        return returnLs;
    }

    //获取班组信息
    public List<DepartmentVo> getGroups(int levelId) {
        List<DepartmentVo> departmentVos = new ArrayList<>();
        String hql = "from Department where valid=1 and levelId=" + levelId;
        List<Department> allDepartment = this.departmentDAO.getResultByQueryString(hql);
        for (Department department : allDepartment) {
            DepartmentVo vo = getVoById(department.getDepartmentId());
            departmentVos.add(vo);
        }
        return departmentVos;
    }

    //获取未删除的所有组织结构信息
    public List getUndeleted() {
        List<Department> returnLs = new ArrayList<Department>();

        List allDepartment = this.departmentDAO.getAll();
        for (Iterator it = allDepartment.iterator(); it.hasNext(); ) {
            Department department = (Department) it.next();
            if (department.isValid())
                returnLs.add(department);
        }

        return returnLs;
    }


    /**
     * 按照树状结构组织Department信息
     *
     * @param xId: 根分类Id
     */
    public ArrayList getDepartmentAsTree(Integer xId) {
        ArrayList arrayDepartment = new ArrayList();

        Department department = (Department) this.departmentDAO.get(xId);
        if (department != null) {
            arrayDepartment.add(department);

            //get sub depart
            addSubDepartmentToArray(arrayDepartment, department);
        }

        return arrayDepartment;
    }

    private void addSubDepartmentToArray(ArrayList array, Department department) {
        List<Department> childs = new ArrayList<Department>(department.getChildren());
        //按照orderNo排序
        //Collections.sort(childs, new BeanComparator("orderNo"));
        Iterator it = childs.iterator();

        while (it.hasNext()) {
            Department subDepartment = (Department) it.next();
            array.add(subDepartment);

            addSubDepartmentToArray(array, subDepartment);
        }
    }

    public Department getDepartment(Integer xId) {
        return this.departmentDAO.get(xId);
    }

    /**
     * 根据departmentId得到Department
     */
    public Department getDepartmentByDeparmentId(Integer departmentId) {
        Department department = new Department();
        String sql = "from Department where valid = 1 and departmentId='" + departmentId + "'";
        List<Department> list = this.departmentDAO.getResultByQueryString(sql);

        if (list != null && list.size() > 0) {
            department = list.get(0);
        }

        return department;
    }

    @Override
    public void disable(Department department) {
        department.setValid(false);
        this.departmentDAO.save(department);

        List<Department> childrenUnits = findAllChildren(department);
        for (Department childUnit : childrenUnits) {
            childUnit.setValid(false);
            this.departmentDAO.save(childUnit);

        }
    }

    @Override
    public DepartmentVo getVoById(Integer departmentId) {
        Department department = getDepartmentByDeparmentId(departmentId);
        DepartmentVo departmentVo = new DepartmentVo();
        departmentVo.setDepartmentId(departmentId);
        if (department.getParent() != null) {
            departmentVo.setParentId(department.getParent().getxId());
        }
        departmentVo.setDepartmentName(department.getDepartmentName());
        departmentVo.setDepartmentNo(department.getDepartmentNo());
        departmentVo.setLevelId(department.getLevelId());
        departmentVo.setLayer(department.getLayer());
        departmentVo.setXId(department.getxId());
        return departmentVo;
    }

    public List<Department> findAllChildren(Department department) {
        List<Department> list = new ArrayList<Department>();

        addAllSubDepartmentToArray(list, department);

        return list;
    }

    private void addAllSubDepartmentToArray(List<Department> array, Department department) {
        List<Department> childs = new ArrayList<Department>(department.getChildren());
        Iterator it = childs.iterator();

        while (it.hasNext()) {
            Department subOrganize = (Department) it.next();

            array.add(subOrganize);

            addAllSubDepartmentToArray(array, subOrganize);
        }

    }


    @Override
    public Department getDepartmentByName(String departmentName) {
        Department department = new Department();
        String sql = "from Department where valid = 1 and departmentName='" + departmentName + "'";
        List<Department> list = this.departmentDAO.getResultByQueryString(sql);

        if (list != null && list.size() > 0) {
            department = list.get(0);
        }

        return department;
    }


//	public List<DepartmentDTO> getDepartmentDTO() {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	//获取指定用户的部门信息
//	public List getDepFromUsers(List users) {
//		
//		List returnDeps = new ArrayList();
//		
//		if (users != null && users.size() > 0) {
//			for (Iterator it=users.iterator();it.hasNext();) {
//				User user = (User)it.next();
//				if (StringUtil.isNotEmpty(user.getDepartmentId())) {
//					if (!returnDeps.contains(user.getPerson().getDepartment())) {
//						returnDeps.add(user.getPerson().getDepartment());
//					}
//				}
//			}
//		}
//		
//		return returnDeps;
//	}


}

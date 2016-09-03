package com.dyrs.smjj.itracker.control;

import com.dyrs.smjj.itracker.entity.Department;

import javax.ejb.Stateless;

/**
 * Created by wangcl on 16-9-3.
 */
@Stateless
public class DepartmentDao extends AbstractDao<Department> {
    public DepartmentDao() {
        super(Department.class);
    }
}

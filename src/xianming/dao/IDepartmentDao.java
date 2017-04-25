package xianming.dao;

import java.util.List;

import xianming.model.Department;

public interface IDepartmentDao extends IBaseDao<Department> {
	/**
	 * 根据部门来获取所有的可以发文的部门id列表
	 * @param depId
	 * @return
	 */
	public List listAllExistIds(int depId);
	
	public List<Department> ListByIds(Integer[] ids);
}

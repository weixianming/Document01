package xianming.dao;


import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import xianming.model.Department;

@Repository("departmentDao")
public class DepartmentDao extends BaseDao<Department> implements IDepartmentDao {

	@Override
	public List listAllExistIds(int depId) {
		String hql = "select ds.scopeDep.id from DepScope ds where ds.depId=?";
		
		return (List)this.listByObj(hql, depId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Department> ListByIds(Integer[] ids) {
		Query q = this.getSession().createQuery("select new Department(d.id,d.name) from Department d where d.id in (:ids)");
		q.setParameterList("ids", ids);
		return q.list();
	}

	
}

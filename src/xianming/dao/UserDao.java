package xianming.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import xianming.model.User;

@SuppressWarnings("unchecked")
@Repository("userDao")
public class UserDao extends BaseDao<User> implements IUserDao {

	@Override
	public List<User> listByIds(Integer[] userIds) {
		Query q = this.getSession().createQuery("select new User(u.id) from User u where u.id in (:ids)");
		q.setParameterList("ids", userIds);
		return q.list();
	}

	@Override
	public List<User> listAllCanSendUser(int userId) {
		String sql = "select t3.id,t3.nickname from t_user t1 left join t_dep_scope t2 on(t1.dep_id=t2.dep_id)"
				+ " right join t_user t3 on(t2.s_dep_id=t3.dep_id) where t1.id=?";
		//addEntity会自动将对象的关联值设置进去，但是对处于department而言，没有意义设置进去，所有可以直接通过非管理对象来操作
//		return this.getSession().createSQLQuery(sql).addEntity(User.class)
//				.setParameter(0, userId).list();
		return this.getSession().createSQLQuery(sql)
				.setResultTransformer(Transformers.aliasToBean(User.class)).setParameter(0, userId).list();
	}


}

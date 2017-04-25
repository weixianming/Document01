package xianming.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import xianming.dao.IDepartmentDao;
import xianming.dao.IUserDao;
import xianming.exception.DocumentException;
import xianming.model.Department;
import xianming.model.Pager;
import xianming.model.User;

@Service("userService")
public class UserService implements IUserService {
	
	private IUserDao userDao;
	private IDepartmentDao departmentDao;
	
	public IUserDao getUserDao() {
		return userDao;
	}
	@Resource
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	
	public IDepartmentDao getDepartmentDao() {
		return departmentDao;
	}
	@Resource
	public void setDepartmentDao(IDepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
	@Override
	public void add(User user, int depId) {
		if(this.loadByUsername(user.getUsername())!=null){
			throw new DocumentException("要添加到用户已经存在不能添加");
		}
		Department dep = departmentDao.load(depId);
		user.setDepartment(dep);
		userDao.add(user);
	}

	@Override
	public void delete(int id) {
		//TODO 需要加入删除关联对象的代码，需要关联删除文档和消息等对象
		userDao.delete(id);
		
	}

	@Override
	public void update(User user,int depId) {
		Department dep = departmentDao.load(depId);
		user.setDepartment(dep);
		userDao.update(user);
	}

	@Override
	public User load(int id) {
		return userDao.load(id);
	}

	@Override
	public Pager<User> findUserByDep(Integer depId) {
		Pager<User> users = null;
		if(depId==null||depId<=0){
			users = userDao.find("from User u  left join fetch u.department");
		}else{
			users = userDao.find("from User u left join fetch u.department dep where dep.id=?",depId);
		}
		return users;
	}
	
	
	private User loadByUsername(String username){
		String hql = "select u from User u where u.username=?";
		return (User)userDao.queryByHql(hql,username);
		
	}
	@Override
	public User login(String username, String password) {
		User u = this.loadByUsername(username);
		if(u==null){
			throw new DocumentException("用户名不存在！");
		}
		if(!u.getPassword().equals(password)){
			throw new DocumentException("用户密码不正确");
		}
		return u;
	}
	@Override
	public void update(User user) {
		userDao.update(user);
	}
	@Override
	public List<User> listAllSendUser(int userId) {
		
		return userDao.listAllCanSendUser(userId);
	}
}

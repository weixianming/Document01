package xianming.service;

import java.util.List;

import xianming.model.Pager;
import xianming.model.User;

public interface IUserService {
	public void add(User user,int depId);
	public void delete(int id);
	public void update(User user,int depId);
	
	public void update(User user);
	
	public User load(int id);
	/**
	 * 根据部门获取用户，如果DepID为了空表示获取所有用户
	 * @param depId
	 * @return
	 */
	public Pager<User> findUserByDep(Integer depId);
	
	public User login(String username,String password);
	
	/**
	 * 根据用户id获取能够发送信息的所有用户
	 * @param userId
	 * @return
	 */
	public List<User> listAllSendUser(int userId);
	
}

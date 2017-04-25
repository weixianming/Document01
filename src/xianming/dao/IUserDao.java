package xianming.dao;

import java.util.List;

import xianming.model.User;

public interface IUserDao extends IBaseDao<User>{
	
	/**
	 * 通过一组用户id来获取一组用户对象
	 * @param userIds
	 * @return
	 */
	public List<User> listByIds(Integer[] userIds);
	
	/**
	 * 获取某个用户可以发送信息的用户
	 * @param userId
	 * @return
	 */
	public List<User> listAllCanSendUser(int userId);
}

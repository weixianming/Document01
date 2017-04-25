package xianming.dao;

import xianming.model.Message;
import xianming.model.UserMessage;

public interface IMessageDao extends IBaseDao<Message> {
	/**
	 * 检查某个用户是否已经读过该msg
	 * @param userId 要检查的用户ID
	 * @param msgId 私人信件ID
	 * @return
	 */
	public boolean checkIsRead(int userId,int msgId);
	
	/**
	 * 根据用户id和私人信件id加载UserMessage对象
	 * @param userId
	 * @param msgId
	 * @return
	 */
	public UserMessage loadUserMessage(int userId,int msgId);
}

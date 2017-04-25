package xianming.dao;


import org.springframework.stereotype.Repository;

import xianming.model.Message;
import xianming.model.UserMessage;

@Repository("messageDao")
public class MessageDao extends BaseDao<Message> implements IMessageDao {

	@Override
	public boolean checkIsRead(int userId, int msgId) {
		String hql ="select um.isRead from UserMessage um where um.user.id=? and um.message.id=?";
		Integer isRead = (Integer)this.queryByHql(hql,new Object[]{userId,msgId});
		if(isRead==null||isRead==0){
			return false;
		}
		return true;
	}

	@Override
	public UserMessage loadUserMessage(int userId, int msgId) {
		String hql ="select um from UserMessage um where um.user.id=? and um.message.id=?";
		return (UserMessage)this.queryByHql(hql,new Object[]{userId,msgId});
	}

}

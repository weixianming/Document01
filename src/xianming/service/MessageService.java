package xianming.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import xianming.dao.IAttachmentDao;
import xianming.dao.IMessageDao;
import xianming.dao.IUserDao;
import xianming.dto.AttachDto;
import xianming.model.Attachment;
import xianming.model.Message;
import xianming.model.Pager;
import xianming.model.SystemContext;
import xianming.model.User;
import xianming.model.UserMessage;
import xianming.util.DocumentUtil;

@Service("messageService")
public class MessageService implements IMessageService {

	private IMessageDao messageDao;
	private IUserDao userDao;
	private IAttachmentDao attachmentDao;
	
	
	
	public IAttachmentDao getAttachmentDao() {
		return attachmentDao;
	}

	@Resource
	public void setAttachmentDao(IAttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	@Resource
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public IMessageDao getMessageDao() {
		return messageDao;
	}
	
	@Resource
	public void setMessageDao(IMessageDao messageDao) {
		this.messageDao = messageDao;
	}

	@Override
	public void add(Message msg, Integer[] userIds,AttachDto ad) throws IOException {
		
		//2、设置该信件的发件人为当前的登录用户
		msg.setUser(SystemContext.getLoginUser());
		msg.setCreateDate(new Date());
		messageDao.add(msg);
		//3、添加一种
		UserMessage um = null;
		//根据用户id获取一组用户信息
		List<User> users = userDao.listByIds(userIds);
		//为这组用户添加相应的用户信息	
		for(User u:users){
			um = new UserMessage();
			um.setIsRead(0);
			um.setMessage(msg);
			um.setUser(u);
			messageDao.addObject(um);
		}
		//添加附件
		DocumentUtil.addAttach(ad, attachmentDao, msg, null);
	}
	


	@Override
	public void deleteReceive(int msgId) {
		User user = SystemContext.getLoginUser();
		String hql = "delete UserMessage um where um.message.id=? and um.user.id=?";
		messageDao.executeByHql(hql, new Object[]{msgId,user.getId()});
	}

	@Override
	public void deleteSend(int msgId) {
		//1、删除所有的已经接收到id
		String hql = "delete UserMessage um where um.message.id=?";
		messageDao.executeByHql(hql,msgId);
		//删除附件
		List<Attachment> atts = this.listAttachmentByMsg(msgId);
		hql = "delete Attachment att where att.message.id=?";
		messageDao.executeByHql(hql, msgId);
		//2、删除msg对象
		messageDao.delete(msgId);
		String path = SystemContext.getRealPath()+"/upload";
		for(Attachment att:atts){
			File f = new File(path+"/"+att.getNewName());
			f.delete();
		}
	}

	@Override
	public Message updateRead(int id, int isRead) {
		if(isRead==0){
			User user = SystemContext.getLoginUser();
			UserMessage um = messageDao.loadUserMessage(user.getId(), id);
			if(um.getIsRead()==0){
				//如果没有读过需要更新为已读
				um.setIsRead(1);
				messageDao.updateObject(um);
			}
		}
		return messageDao.load(id);
	}

	@Override
	public Pager<Message> findSendMessage(String con) {
		User user = SystemContext.getLoginUser();
		String hql = "select new Message(msg.id,msg.title,msg.content,msg.createDate) from Message msg where msg.user.id = ?";
		if(con!=null&&!"".equals(con.trim())){
			hql+=" and (msg.title like ? or msg.content like ?) order by msg.createDate desc";
			return messageDao.find(hql, new Object[]{user.getId(),"%"+con+"%","%"+con+"%"});
		}
		hql+="order by msg.createDate desc";
		return messageDao.find(hql, user.getId());
	}

	@Override
	public Pager<Message> findReceiveMessage(String con, int isRead) {
		User user = SystemContext.getLoginUser();
		String hql = "select um.message from UserMessage um left join fetch um.message.user mu left join fetch mu.department where um.isRead=? and um.user.id=?";
		if(con!=null&&!"".equals(con.trim())){
			hql+=" and (um.message.title like ? or um.message.content like ?) order by um.message.createDate desc";
			return messageDao.find(hql, new Object[]{isRead,user.getId(),"%"+con+"%","%"+con+"%"});
		}
		hql+="order by um.message.createDate desc";
		return messageDao.find(hql,new Object[]{isRead,user.getId()});
	}

	@Override
	public List<Attachment> listAttachmentByMsg(int msgId) {
		String hql = "from Attachment att where att.message.id=?";
		return attachmentDao.list(hql,msgId);
	}

}

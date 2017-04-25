package xianming.action;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import xianming.dto.AttachDto;
import xianming.model.Message;
import xianming.model.User;
import xianming.service.IMessageService;
import xianming.service.IUserService;
import xianming.util.ActionUtil;

@Controller("messageAction")
@Scope("prototype")
public class MessageAction extends ActionSupport implements ModelDriven<Message>{
	private IMessageService messageService;
	private IUserService userService;
	private Message message;
	private int isRead;
	private String con;
	private Integer[] sus;
	private File[] atts;
	private String[] attsContentType;
	private String[] attsFileName;
	
	
	public File[] getAtts() {
		return atts;
	}

	public void setAtts(File[] atts) {
		this.atts = atts;
	}

	public String[] getAttsContentType() {
		return attsContentType;
	}

	public void setAttsContentType(String[] attsContentType) {
		this.attsContentType = attsContentType;
	}

	public String[] getAttsFileName() {
		return attsFileName;
	}

	public void setAttsFileName(String[] attsFilename) {
		this.attsFileName = attsFilename;
	}

	public Integer[] getSus() {
		return sus;
	}

	public void setSus(Integer[] sus) {
		this.sus = sus;
	}

	public IUserService getUserService() {
		return userService;
	}

	@Resource
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public String getCon() {
		return con;
	}

	public void setCon(String con) {
		this.con = con;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public IMessageService getMessageService() {
		return messageService;
	}

	@Resource
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}
	
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@Override
	public Message getModel() {
		if(message==null){
			message = new Message();
		}
		return message;
	}
	
	public String listSend(){
		ActionContext.getContext().put("pages", messageService.findSendMessage(con));
		
		return SUCCESS;
	}
	
	public String listReceive(){
		ActionContext.getContext().put("pages", messageService.findReceiveMessage(con, isRead));
		return SUCCESS;
	}
	
	public String deleteSend(){
		messageService.deleteSend(message.getId());
		ActionUtil.setUrl("/message_listSend.action");
		return ActionUtil.REDIRECT;
	}
	
	public String deleteReceive(){
		messageService.deleteReceive(message.getId());
		ActionUtil.setUrl("/message_listReceive.action");
		return ActionUtil.REDIRECT;
	}
	
	public String show() throws IllegalAccessException, InvocationTargetException{
		Message tm = messageService.updateRead(message.getId(),isRead);
		BeanUtils.copyProperties(message, tm);
		ActionContext.getContext().put("atts", messageService.listAttachmentByMsg(message.getId()));
		return  SUCCESS;
	}
	
	public void validateAdd(){
		if(sus==null||sus.length<=0){
			this.addFieldError("sus", "必须选择要发送到用户");
		}
		if(message.getTitle()==null||"".equals(message.getTitle().trim())){
			this.addFieldError("title", "私人信件的标题不能为空");
		}
		if(this.hasFieldErrors()){
			addInput();
		}
		
	}
	
	public String add() throws IOException{
		if(atts==null||atts.length<=0){
			messageService.add(message, sus,new AttachDto(false));
		} else{
			String uploadPath = ServletActionContext.getServletContext().getRealPath("upload");
			uploadPath = "F:\\STSworkspace4\\document01\\WebContent\\upload";
			messageService.add(message, sus, new AttachDto(atts,attsContentType,attsFileName,uploadPath));
		}
		ActionUtil.setUrl("/message_listSend.action");
		return ActionUtil.REDIRECT;
	}
	
	public String addInput(){
		User u = (User)ActionContext.getContext().getSession().get("loginUser");
		ActionContext.getContext().put("us", userService.listAllSendUser(u.getId()));
		return SUCCESS;
	}
	
}

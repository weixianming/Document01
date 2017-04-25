package xianming.action;

import java.lang.reflect.InvocationTargetException;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import xianming.model.User;
import xianming.service.IDepartmentService;
import xianming.service.IUserService;
import xianming.service.UserService;
import xianming.util.ActionUtil;

@Controller("userAction")
@Scope("prototype")
public class UserAction extends ActionSupport implements ModelDriven<User>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -134039343509527260L;
	private User user;
	private Integer depId;
	private IDepartmentService departmentService;
	
	
	public IDepartmentService getDepartmentService() {
		return departmentService;
	}

	@Resource
	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public Integer getDepId() {
		return depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	private IUserService userService;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public IUserService getUserService() {
		return userService;
	}
	
	@Resource
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@Override
	public User getModel() {
		if(user==null){
			user = new User();
		}
		return user;
	}
	
	public String list(){
		ActionContext.getContext().put("ds", departmentService.listAllDep());
		ActionContext.getContext().put("pages", userService.findUserByDep(depId));
		return SUCCESS;
	}
	
	public String addInput(){
		ActionContext.getContext().put("ds", departmentService.listAllDep());
		return SUCCESS;
	}
	
	public String add(){
		userService.add(user, depId);
		ActionUtil.setUrl("/user_list.action");
		return ActionUtil.REDIRECT;
	}
	
	public void validateAdd(){
		if(ActionUtil.isEmpty(user.getUsername())){
			this.addFieldError("username", "用户名称不能为空");
		}
	}
	
	public String updateInput() throws IllegalAccessException, InvocationTargetException{
		User tu = userService.load(user.getId());
		BeanUtils.copyProperties(user, tu);
		ActionContext.getContext().put("ds", departmentService.listAllDep());
		return SUCCESS;
		
	}
	
	public String update(){
		User tu = userService.load(user.getId());
		tu.setEmail(user.getEmail());
		tu.setNickname(user.getNickname());
		tu.setType(user.getType());
		userService.update(tu, depId);
		ActionUtil.setUrl("/user_list.action");
		return ActionUtil.REDIRECT;
	}
	
	public String show() throws IllegalAccessException, InvocationTargetException{
		User tu = userService.load(user.getId());
		BeanUtils.copyProperties(user, tu);
		
		return SUCCESS;
	}
	
	public String delete(){
		userService.delete(user.getId());
		ActionUtil.setUrl("/user_list.action");
		return ActionUtil.REDIRECT;
	}
	
	public String updateSelfInput() throws IllegalAccessException, InvocationTargetException{
		User tu = (User)ActionContext.getContext().getSession().get("loginUser");
		BeanUtils.copyProperties(user, tu);
		return SUCCESS;
	}
	
	public String updateSelf(){
		User tu = userService.load(user.getId());
		tu.setEmail(user.getEmail());
		tu.setNickname(user.getNickname());
		userService.update(tu);
		ActionContext.getContext().getSession().put("loginUser", tu);
		ActionUtil.setUrl("/user_showSelf.action");
		return ActionUtil.REDIRECT;
	}
	
	public String showSelf() throws IllegalAccessException, InvocationTargetException{
		User tu = (User)ActionContext.getContext().getSession().get("loginUser");
		BeanUtils.copyProperties(user, tu);
		return SUCCESS;
	}
}

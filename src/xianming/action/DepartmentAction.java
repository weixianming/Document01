package xianming.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import xianming.model.Department;
import xianming.service.IDepartmentService;
import xianming.util.ActionUtil;

@Controller("departmentAction")
@Scope("prototype")
public class DepartmentAction extends ActionSupport implements ModelDriven<Department>{
	private IDepartmentService departmentService;
	private Department department;
	private int[] sdeps;
	
	
	public int[] getSdeps() {
		return sdeps;
	}
	public void setSdeps(int[] sdeps) {
		this.sdeps = sdeps;
	}
	public IDepartmentService getDepartmentService() {
		return departmentService;
	}
	@Resource
	public void setDepartmentService(IDepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}


	@Override
	public Department getModel() {
		if(department==null){
			department = new Department();
		}
		return department;
	}
	
	public String list(){
		ActionContext.getContext().put("ds", departmentService.listAllDep());
		return SUCCESS;
	}
	
	public String addInput(){
		return SUCCESS;
	}
	
	public String add(){
		departmentService.add(department);
		ActionUtil.setUrl("/department_list.action");
		return ActionUtil.REDIRECT;
	}
	
	public String delete(){
		departmentService.delete(department.getId());
		ActionUtil.setUrl("/department_list.action");
		return ActionUtil.REDIRECT;
	}
	
	public void validateAdd(){
		if(ActionUtil.isEmpty(department.getName())){
			this.addFieldError("name", "部门名称不能为空");
		}
	}
	
	public String updateInput(){
		Department tp = departmentService.load(department.getId());
		department.setId(tp.getId());
		department.setName(tp.getName());
		return SUCCESS;
	}
	
	public String update(){
		Department tp = departmentService.load(department.getId());
		tp.setName(department.getName());
		departmentService.update(tp);
		ActionUtil.setUrl("/department_list.action");
		return ActionUtil.REDIRECT;
	}
	
	public void validateUpdate(){
		if(ActionUtil.isEmpty(department.getName())){
			this.addFieldError("name", "部门名称不能为空");
		}

	}
	
	public String setDepScopeInput(){
		department = departmentService.load(department.getId());
		List<Integer> ads = departmentService.listDepScopeDepId(department.getId());
		ActionContext.getContext().put("ads", ads);
		List<Department> allDep = departmentService.listAllDep();
		//将当前部门移除
		System.out.println(allDep.size());
		allDep.remove(department);
		System.out.println(allDep.size());
		ActionContext.getContext().put("ds", allDep);
		return SUCCESS;
	}
	
	public String setDepScope(){
		departmentService.addScopeDeps(department.getId(), sdeps);
		ActionUtil.setUrl("/department_setDepScopeInput.action?id="+department.getId());
		return ActionUtil.REDIRECT;
	}
	
	public String show(){
		department = departmentService.load(department.getId());
		ActionContext.getContext().put("ds", departmentService.listDepScopeDep(department.getId()));
		return SUCCESS;
	}
}

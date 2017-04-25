package xianming.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import xianming.model.Document;
import xianming.model.User;
import xianming.service.IDepartmentService;
import xianming.service.IDocumentService;

@Controller("documentAction")
@Scope("prototype")
public class DocumentAction extends ActionSupport implements ModelDriven<Document> {
	
	private Document document;
	private Integer isRead;
	private String con;
	private Integer depId;
	private IDocumentService documentService;
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

	public IDocumentService getDocumentService() {
		return documentService;
	}

	@Resource
	public void setDocumentService(IDocumentService documentService) {
		this.documentService = documentService;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public String getCon() {
		return con;
	}

	public void setCon(String con) {
		this.con = con;
	}

	@Override
	public Document getModel() {
		if(document==null){
			document = new Document();
		}
		return document;
	}
	
	public String listReceive(){
		ActionContext.getContext().put("deps", departmentService.listAllDep());
		if(isRead==null||isRead==0){
			ActionContext.getContext().put("pages", documentService.findNotReadDocument(con, depId));
		}else{
			ActionContext.getContext().put("pages", documentService.findReadDocument(con, depId));
		}
		return SUCCESS;
	}
	
	public String listSend(){
		ActionContext.getContext().put("deps", departmentService.listAllDep());
		User u = (User)ActionContext.getContext().getSession().get("loginUser");
		ActionContext.getContext().put("pages", documentService.findSendDocument(u.getId()));
		return SUCCESS;
	}
	
}

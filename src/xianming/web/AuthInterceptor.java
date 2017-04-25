package xianming.web;

import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import xianming.exception.DocumentException;
import xianming.model.User;
import xianming.util.ActionUtil;

@SuppressWarnings("serial")
@Component("authInterceptor")
public class AuthInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String an = invocation.getProxy().getActionName();
		if(!an.startsWith("login")){
			User loginUser = (User)ActionContext.getContext().getSession().get("loginUser");
			if(loginUser == null){
				return "login";
			}
			if(loginUser.getType()!=1){
				//普通用户
				if(!ActionUtil.checkUrl(an)){
					throw new DocumentException("需要管理员才能访问该功能");
				}
			}
		}
		return invocation.invoke();
	}

}

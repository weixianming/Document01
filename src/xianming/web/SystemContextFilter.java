package xianming.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import xianming.model.SystemContext;
import xianming.model.User;

public class SystemContextFilter implements Filter {

	private int pageSize;
	
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			int tps = pageSize;
			try {
				tps = Integer.parseInt(request.getParameter("pageSize"));
			} catch (NumberFormatException e) {}
			int pageOffset = 0;
			try {
				pageOffset = Integer.parseInt(request.getParameter("pager.offset"));
			} catch (NumberFormatException e) {}
			HttpServletRequest hrequest = (HttpServletRequest) request;
			SystemContext.setPageOffset(pageOffset);
			SystemContext.setPageSize(tps);
			String realPath = hrequest.getSession().getServletContext().getRealPath("");
			realPath = "F:\\STSworkspace4\\document01\\WebContent";
			SystemContext.setRealPath(realPath);
			User loginUser = (User)hrequest.getSession().getAttribute("loginUser"); 
			if(loginUser!=null) SystemContext.setLoginUser(loginUser);
			chain.doFilter(request, response);
		} finally {
			SystemContext.removePageSize();
			SystemContext.removePageOffset();
			SystemContext.removeLoginUser();
			SystemContext.removeRealPath();
		}
	}

	@Override
	public void init(FilterConfig cfg) throws ServletException {
		pageSize = Integer.parseInt(cfg.getInitParameter("pageSize"));
	}

}

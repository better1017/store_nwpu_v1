package edu.nwpu.store.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import edu.nwpu.store.domain.User;

@WebFilter({ "/jsp/cart.jsp", "/jsp/order_info.jsp", "/jsp/order_list.jsp" })
public class PriviledgeFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest myReq = (HttpServletRequest) request;
		// 判断当前的session中是否存在已经登陆成功的用户
		User user = (User) myReq.getSession().getAttribute("loginUser");

		if (null != user) {
			// 如果存在，放行
			chain.doFilter(request, response);
		} else {
			// 如果不存在，转到提示界面
			myReq.setAttribute("msg", "请登录之后进行访问！");
			// 转到提示页面
			myReq.getRequestDispatcher("/jsp/info.jsp").forward(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

	public PriviledgeFilter() {

	}

	public void destroy() {

	}

}

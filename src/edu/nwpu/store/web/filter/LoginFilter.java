package edu.nwpu.store.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nwpu.store.domain.User;
import edu.nwpu.store.service.UserService;
import edu.nwpu.store.service.serviceIml.UserServiceIml;
import edu.nwpu.store.utils.CookieUtils;

public class LoginFilter implements Filter {
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
	
	@Override
	public void doFilter(ServletRequest requ, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		// 强转
		HttpServletRequest request = (HttpServletRequest)requ;
		HttpServletResponse response = (HttpServletResponse)resp;
		
		// 如果是登录页直接放行
		String servletPath = request.getServletPath();
		if(servletPath.startsWith("/UserServlet")) {
			String method = request.getParameter("method");
			if ("loginUI".equals(method)) {
				chain.doFilter(request, response);
				return;
			}
		}
		
		// 1.用户登录信息（已登录后存在Session中的数据）
		User loginUser = (User)request.getSession().getAttribute("loginUser");
		
		// 2.如果已经登录，放行，不需要自动登录
		if (null != loginUser) {
			chain.doFilter(request, response);
			return; // 程序结束
		}
		
		// 3.获取自动登录Cookie信息
		Cookie autoLognCookie = CookieUtils.getCookieByName("autoLoginCookie", request.getCookies());
		
		// 4.判断自动登录Cookie是否存在，如果没有Cookie，不需要自动
		if (null == autoLognCookie) {
			chain.doFilter(request, response);
			return;
		}
		
		// 5.通过用户Cookie中记录信息查询用户
		// 5.1.获取用户信息
		String[] userStrs = autoLognCookie.getValue().split("@");
		String userName = userStrs[0];
		String userPWD = userStrs[1];
		User user = new User(userName, userPWD);
		try {
			// 5.2.执行登录
			UserService us = new UserServiceIml();
			loginUser = us.userLogin(user);
			// 6.如果没有查询到
			if (null == loginUser) {
				chain.doFilter(request, response);
				return;
			}
			
			// 7.自动登录
			request.getSession().setAttribute("loginUser", loginUser);
			// 放行
			chain.doFilter(request, response);
		} catch (Exception e) {
			System.out.println("自动登录异常，自动忽略！");
		}
	}
	
	@Override
	public void destroy() {
	}
	
}

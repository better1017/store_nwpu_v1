package edu.nwpu.store.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nwpu.store.domain.User;
import edu.nwpu.store.service.UserService;
import edu.nwpu.store.service.serviceIml.UserServiceIml;
import edu.nwpu.store.utils.MailUtils;
import edu.nwpu.store.utils.MyBeanUtils;
import edu.nwpu.store.utils.UUIDUtils;
import edu.nwpu.store.web.base.BaseServlet;

@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	// registerUI：跳转到注册页面
	public String registerUI(HttpServletRequest request, HttpServletResponse response) {
		return "/jsp/register.jsp";
	}

	// userRegister：真正的注册实现
	public String userRegister(HttpServletRequest request, HttpServletResponse response) {

		try {
			User user = new User();
			// 接收表单参数
			MyBeanUtils.populate(user, request.getParameterMap());
			// 为用户的其他属性赋值：uid，state，code
			user.setUid(UUIDUtils.getId());
			user.setState(0);
			user.setCode(user.getUid() + user.getUid());
			// 调用业务层注册功能
			UserService us = new UserServiceIml();
			us.userRegister(user);
			// 注册成功就返回注册成功的信息，失败则捕捉异常返回注册失败的信息
			request.setAttribute("msg", "用户注册成功，请激活！");
			// 注册成功，向用户邮箱发送激活信息
			MailUtils.sendMail(user.getEmail(), user.getCode());
		} catch (Exception e) {
			e.printStackTrace();
			// 失败则捕捉异常返回注册失败的信息
			request.setAttribute("msg", "用户注册失败，请重新注册！");
		}
		return "/jsp/info.jsp";
	}

	// activeAccount：激活账号
	public String activeAccount(HttpServletRequest request, HttpServletResponse response) {
		// 获取激活码
		String code = request.getParameter("code");
		// 调用业务层激活功能
		UserService us = new UserServiceIml();

		boolean flag = false;
		try {
			flag = us.activeAccount(code);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		// 进行激活的信息提示
		if (flag == true) {
			// 用户激活成功，向request放入提示信息，转发到登录界面
			request.setAttribute("msg", "您已经成功激活账号，请登录！");
			return "/jsp/login.jsp";
		} else {
			// 用户激活成功，向request放入提示信息，转发到失败信息提示页面
			request.setAttribute("msg", "激活码无效，激活失败！");
			return "/jsp/info.jsp";
		}
	}

	// loginUI：跳转到登录页面
	public String loginUI(HttpServletRequest request, HttpServletResponse response) {
		return "/jsp/login.jsp";
	}

	// userLogin：用户登录的实际实现
	public String userLogin(HttpServletRequest request, HttpServletResponse response) {
		// 获取用户数据（账户 + 密码）并封装
		User user = MyBeanUtils.populate(User.class, request.getParameterMap());
		// 调用业务层登录功能
		UserService us = new UserServiceIml();
		try {
			// user的判null在serviceiml中做了
			user = us.userLogin(user);

			// 用户登录成功，将用户信息放入session中
			request.getSession().setAttribute("loginUser", user);

			// 处理自动登录的业务
			String autoLogin = request.getParameter("autoLogin");
			// 如果勾选，用cookie记录用户信息
			if ("1".equals(autoLogin)) {
				Cookie autoLoginCookie = new Cookie("autoLoginCookie", user.getUsername() + "@" + user.getPassword());
				autoLoginCookie.setPath("/"); // cookie保存路径
				autoLoginCookie.setMaxAge(60 * 60 * 24 * 7); // 保存cookie的时间
				response.addCookie(autoLoginCookie);
			} else { // 如果没勾选，删除cookie
				Cookie autoLoginCookie = new Cookie("autoLoginCookie", "");
				autoLoginCookie.setPath("/");
				autoLoginCookie.setMaxAge(0);
				response.addCookie(autoLoginCookie);
			}

			// 重定向到首页
			response.sendRedirect("/store_nwpu_v1/index.jsp");
			return null;
		} catch (Exception e) {
			// 用户登录失败
			String msg = e.getMessage();
			// System.out.println(msg);
			// 向request中放入登录失败的信息
			request.setAttribute("msg", msg);
			// 转发到登录界面
			return "/jsp/login.jsp";
		}
	}

	// logout:：用户退出
	public String logout(HttpServletRequest request, HttpServletResponse response) {

		// 清除session
		request.getSession().invalidate();

		// 清除cookie：否则会无法退出
		Cookie autoLoginCookie = new Cookie("autoLoginCookie", "");
		autoLoginCookie.setPath("/");
		autoLoginCookie.setMaxAge(0);
		response.addCookie(autoLoginCookie);

		try {
			// 重定向到首页
			response.sendRedirect("/store_nwpu_v1/index.jsp");
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return null;
	}
}

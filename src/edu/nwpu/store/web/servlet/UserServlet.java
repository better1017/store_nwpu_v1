package edu.nwpu.store.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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

	// registerUI
	public String registerUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/jsp/register.jsp";
	}

	// userRegister
	public String userRegister(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			User user = new User();
			MyBeanUtils.populate(user, request.getParameterMap());
			user.setUid(UUIDUtils.getId());
			user.setState(0);
			user.setCode(user.getUid()+user.getUid());
			//System.out.println(user);
			UserService us = new UserServiceIml();
			us.userRegister(user);
			MailUtils.sendMail(user.getEmail(), user.getCode());
			request.setAttribute("msg", "用户注册成功，请激活！");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "用户注册失败，请重新注册！");
		}
		return "/jsp/info.jsp";
	}
	public String activeAccount(){
		
		return "/jsp/info.jsp";
	}
}

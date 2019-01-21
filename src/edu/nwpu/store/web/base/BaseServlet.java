package edu.nwpu.store.web.base;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BaseServlet")
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String md = request.getParameter("method");
		if (null == md || "".equals(md) || "".equals(md.trim())) {
			md = "defaultMethod";
		}
		Class clazz = this.getClass();
		try {
			Method method = clazz.getMethod(md, HttpServletRequest.class, HttpServletResponse.class);
			if(null!=method){
				String jspPath = (String) method.invoke(this, request, response);
				if (null != jspPath) {
					request.getRequestDispatcher(jspPath).forward(request, response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String defaultMethod(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}

}

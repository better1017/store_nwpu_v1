package edu.nwpu.store.web.base;

import java.lang.reflect.Method;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BaseServlet")
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response){
		String md = request.getParameter("method");
		if (null == md || "".equals(md) || "".equals(md.trim())) {
			md = "execute";
		}
		
		// 注意：此处的this代表的是子类的对象
		// 子类对象的字节码对象
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

	// 默认方法
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

}

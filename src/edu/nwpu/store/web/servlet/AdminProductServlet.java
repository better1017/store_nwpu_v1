package edu.nwpu.store.web.servlet;

import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nwpu.store.domain.PageModel;
import edu.nwpu.store.service.ProductService;
import edu.nwpu.store.service.serviceIml.ProductServiceImp;
import edu.nwpu.store.web.base.BaseServlet;
@WebServlet("/AdminProductServlet")
public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	//findAllProductsWithPage
	public String findAllProductsWithPage(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		//获取当前页
		int curNum = Integer.parseInt(request.getParameter("num"));
		//调用业务层查询全部商品信息返回PageModel
		ProductService ps = new ProductServiceImp();
		PageModel pm = ps.findAllProductsWithPage(curNum);
		//将PageModel放入request
		//System.out.println(pm.getList());
		request.setAttribute("page", pm);
		//转发到/admin/product/list.jsp
		return "/admin/product/list.jsp";
	}
}

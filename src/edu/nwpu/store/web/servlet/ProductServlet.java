package edu.nwpu.store.web.servlet;

import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nwpu.store.domain.PageModel;
import edu.nwpu.store.domain.Product;
import edu.nwpu.store.service.ProductService;
import edu.nwpu.store.service.serviceIml.ProductServiceImp;
import edu.nwpu.store.web.base.BaseServlet;

@WebServlet("/ProductServlet")
public class ProductServlet extends BaseServlet {

	public String findProductByPid(HttpServletRequest request, HttpServletResponse response) {
		// 获取商品id
		String pid = request.getParameter("pid");
		// 根据商品id查询到商品信息
		ProductService ps = new ProductServiceImp();
		Product product = null;
		try {
			product = ps.findProductByPid(pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 将获取到的商品放入request
		request.setAttribute("product", product);
		// 转发到jsp/product_info.jsp
		return "jsp/product_info.jsp";
	}

	public String findProductsByCidWithPage(HttpServletRequest request, HttpServletResponse response) {

		// 获取cid,num
		String cid = request.getParameter("cid");
		int curNum = Integer.parseInt(request.getParameter("num"));
		// 调用业务层功能：以分页形式查询当前类别下的商品信息
		ProductService ps = new ProductServiceImp();
		PageModel pm = null;
		try {
			// 返回PageModel对象（1_当前页商品信息2_分页3_url）
			pm = ps.findProductsByCidWithPage(cid, curNum);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 将PageModel对象放入request
		request.setAttribute("page", pm);
		// 转发到/jsp/product_list.jsp
		return "/jsp/product_list.jsp";
	}
}

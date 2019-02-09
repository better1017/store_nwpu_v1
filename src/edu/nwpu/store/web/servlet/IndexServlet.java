package edu.nwpu.store.web.servlet;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nwpu.store.domain.Product;
import edu.nwpu.store.service.ProductService;
import edu.nwpu.store.service.serviceIml.ProductServiceImp;
import edu.nwpu.store.web.base.BaseServlet;

@SuppressWarnings("serial")
@WebServlet("/IndexServlet")
public class IndexServlet extends BaseServlet {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		/*
		// 调用业务层功能：获取全部分类信息，返回集合
		CategoryService cs = new CategoryServiceImp();
		List<Category> categoryList = cs.getAllCategories();
		// 将返回的集合放入request
		request.setAttribute("allCategories", categoryList);
		*/
		// 转发到真实的首页
		//System.out.println("**************************IndexServlet****************************");
		
		// 调用业务层查询最新商品、最热商品，返回两个集合
		ProductService ps = new ProductServiceImp();
		List<Product> list01 = null;
		List<Product> list02 = null;
		try {
			list01 = ps.findHots();
			list02 = ps.findNews();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 将两个集合放入到request
		request.setAttribute("hots", list01);
		request.setAttribute("news", list02);
		// 转发到真实的首页
		return "/jsp/index.jsp";
	}

}

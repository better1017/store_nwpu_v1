package edu.nwpu.store.web.servlet;

import java.util.Iterator;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nwpu.store.domain.Category;
import edu.nwpu.store.service.CategoryService;
import edu.nwpu.store.service.serviceIml.CategoryServiceImp;
import edu.nwpu.store.web.base.BaseServlet;

@WebServlet("/IndexServlet")
public class IndexServlet extends BaseServlet {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 调用业务层功能：获取全部分类信息，返回集合
		CategoryService cs = new CategoryServiceImp();
		List<Category> categoryList = cs.getAllCategories();
		// 将返回的集合放入request
		request.setAttribute("allCategories", categoryList);
		// 转发到真实的首页
		return "/jsp/index.jsp";

	}

}

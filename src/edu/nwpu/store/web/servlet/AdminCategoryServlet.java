package edu.nwpu.store.web.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nwpu.store.domain.Category;
import edu.nwpu.store.service.CategoryService;
import edu.nwpu.store.service.serviceIml.CategoryServiceImp;
import edu.nwpu.store.utils.UUIDUtils;
import edu.nwpu.store.web.base.BaseServlet;

@WebServlet("/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String findAllCats(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取全部分类信息
		CategoryService cs = new CategoryServiceImp();
		List<Category> list = cs.getAllCategories();

		// 全部分类信息放入request
		request.setAttribute("allCats", list);
		// 转发到/admin/category/list.jsp
		return "/admin/category/list.jsp";
	}

	// addCategoryUI
	public String addCategoryUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/admin/category/add.jsp";
	}

	// addCategory
	public String addCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Category category = new Category();
		// 获取分类名称
		category.setCname(request.getParameter("cname"));
		// 创建分类id
		category.setCid(UUIDUtils.getId());
		// 调用业务层添加分类的功能
		CategoryService cs = new CategoryServiceImp();
		cs.addCategory(category);
		// 重定向到查询全部分类信息
		response.sendRedirect("/store_nwpu_v1/AdminCategoryServlet?method=findAllCats");
		return null;
	}
}

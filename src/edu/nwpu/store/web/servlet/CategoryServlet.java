package edu.nwpu.store.web.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nwpu.store.domain.Category;
import edu.nwpu.store.service.CategoryService;
import edu.nwpu.store.service.serviceIml.CategoryServiceImp;
import edu.nwpu.store.utils.JedisUtils;
import edu.nwpu.store.web.base.BaseServlet;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

@WebServlet("/CategoryServlet")
public class CategoryServlet extends BaseServlet {

	public String findAllCats(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 在redis中获取全部分类信息
		Jedis jedis = JedisUtils.getJedis();
		String jsonStr = jedis.get("allCats");
		if (null == jsonStr || "".equals(jsonStr)) {
			// 调用业务层获取全部分类
			CategoryService cs = new CategoryServiceImp();
			List<Category> list = cs.getAllCategories();
			// 将全部分类转换为JSON格式的数据
			jsonStr = JSONArray.fromObject(list).toString();
			jedis.set("allCats", jsonStr);
			// System.out.println("缓存中没有数据！");
			// 将全部分类信息相应到客户端
			// 告诉浏览器本次响应的数据是JSON格式的字符串
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(jsonStr);
		} else {
			// System.out.println("缓存中有数据！");
			// 将全部分类信息相应到客户端
			// 告诉浏览器本次响应的数据是JSON格式的字符串
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(jsonStr);
		}

		// 释放
		JedisUtils.closeJedis(jedis);

		return null;
	}
}
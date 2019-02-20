package edu.nwpu.store.web.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nwpu.store.domain.Order;
import edu.nwpu.store.service.OrderService;
import edu.nwpu.store.service.serviceIml.OrderServiceImp;
import edu.nwpu.store.web.base.BaseServlet;
import net.sf.json.JSONArray;

@WebServlet("/AdminOrderServlet")
public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	// findOrders
	public String findOrders(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String state = request.getParameter("state");
		// 获取到全部订单
		OrderService os = new OrderServiceImp();
		List<Order> list = null;
		if (null == state || "".equals(state)) {
			list = os.findAllOrders();
		} else {
			list = os.findAllOrders(state);
		}
		// 将全部订单放入到request
		request.setAttribute("allOrders", list);
		// 转发到/admin/order/list.jsp
		return "/admin/order/list.jsp";
	}

	// findOrderByOidWithAjax
	public String findOrderByOidWithAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取到订单的id
		String oid = request.getParameter("id");
		// 查询这个订单下所有的订单项以及订单相对应的商品信息,返回集合
		OrderService os = new OrderServiceImp();
		Order order = os.findOrderByOid(oid);
		// 将返回的集合转换为JSON格式字符串
		String jsonStr = JSONArray.fromObject(order.getList()).toString();
		// System.out.println(jsonStr);
		// 响应到客户端
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().print(jsonStr);
		return null;
	}

	// updateOrderByOid&oid
	public String updateOrderByOid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 获取订单的oid
		String oid = request.getParameter("oid");
		// 获取到要修改的状态
		int state = Integer.parseInt(request.getParameter("state"));

		// System.out.println(oid + "\n" + state);

		// 根据订单的oid查询订单
		OrderService os = new OrderServiceImp();
		Order order = os.findOrderByOid(oid);
		System.out.println(order);
		// 设置订单状态
		order.setState(state);
		// 修改订单信息
		os.updateOrder(order);
		// 重新定向到查询已发送订单
		response.sendRedirect("/store_nwpu_v1/AdminOrderServlet?method=findOrders&state=" + state);
		return null;
	}
}

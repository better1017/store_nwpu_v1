package edu.nwpu.store.web.servlet;

import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nwpu.store.domain.Cart;
import edu.nwpu.store.domain.CartItem;
import edu.nwpu.store.domain.Order;
import edu.nwpu.store.domain.OrderItem;
import edu.nwpu.store.domain.PageModel;
import edu.nwpu.store.domain.User;
import edu.nwpu.store.service.OrderService;
import edu.nwpu.store.service.serviceIml.OrderServiceImp;
import edu.nwpu.store.utils.UUIDUtils;
import edu.nwpu.store.web.base.BaseServlet;

@WebServlet("/OrderServlet")
public class OrderServlet extends BaseServlet {

	// saveOrder 将购物车中的信息以订单的形式保存
	public String saveOrder(HttpServletRequest request, HttpServletResponse response) {
		//System.out.println("OrderServlet");
		// 确认用户登录状态
		User user = (User) request.getSession().getAttribute("loginUser");
		if (null == user) {
			request.setAttribute("msg", "请在登录之后下单！");
			return "jsp/info.jsp";
		}
		// 获取购物车
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		// 创建订单对象，为订单对象赋值
		Order order = new Order();
		order.setOid(UUIDUtils.getId());
		order.setOrderTime(new Date());
		order.setTotal(cart.getTotal());
		order.setState(1);//
		order.setUser(user);
		// 遍历购物项的同时, 创建订单项, 为订单项赋值
		for (CartItem cartItem : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setItemid(UUIDUtils.getId());
			orderItem.setQuantity(cartItem.getNum());
			orderItem.setTotal(cartItem.getSubTotal());
			orderItem.setProduct(cartItem.getProduct());

			// 设置当前的订单项属于哪个订单: 程序角度体现订单项和订单对应关系
			orderItem.setOrder(order);
			order.getList().add(orderItem);
		}
		// 调用业务层功能：保存订单
		OrderService os = new OrderServiceImp();
		try {
			// 将订单数据 用户的数据 订单下所有的订单项都传递到了service层
			os.saveOrder(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 清空购物车
		cart.clearCart();
		// 将订单放入request
		request.setAttribute("order", order);
		// 转发/jsp/order_info.jsp
		//System.out.println("end1");
		return "/jsp/order_info.jsp";
	}

	public String findOrdersWithPage(HttpServletRequest request, HttpServletResponse response) {
		//获取用户信息
		User user = (User) request.getSession().getAttribute("loginUser");
		//获取当前页
		int curNum = Integer.parseInt(request.getParameter("num"));
		//调用业务层功能：查询当前用户订单信息，返回PageModel
		OrderService os =new OrderServiceImp();
		//SELECT * FROM orders WHERE uid=? LIMIT ?,?
		//PageModel-pm: 1_分页参数 2_当前用户当前页的订单（集合）, 每笔订单上对应的订单项, 以及订单项对应的商品信息
		PageModel pm = null;
		try {
			pm = os.findMyOrdersWithPage(user, curNum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//将PageModel放入request
		request.setAttribute("page", pm);
		//转发到/jsp/order_list.jsp
		return "/jsp/order_list.jsp";
	}
	
	//findOrderByOid
	public String findOrderByOid(HttpServletRequest request, HttpServletResponse response) {
		//获取到订单的oid
		String oid = request.getParameter("oid");
		//调用业务层功能：根据订单编号查询订单信息
		OrderService os = new OrderServiceImp();
		Order order = null;
		try {
			order = os.findOrderByOid(oid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//将订单放入request
		request.setAttribute("order", order);
		//转发到/jsp/order_info.jsp
		return "/jsp/order_info.jsp";
	}
}

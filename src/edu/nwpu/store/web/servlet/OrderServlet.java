package edu.nwpu.store.web.servlet;

import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nwpu.store.domain.Cart;
import edu.nwpu.store.domain.CartItem;
import edu.nwpu.store.domain.Order;
import edu.nwpu.store.domain.OrderItem;
import edu.nwpu.store.domain.User;
import edu.nwpu.store.service.OrderService;
import edu.nwpu.store.service.serviceIml.OrderServiceImp;
import edu.nwpu.store.utils.UUIDUtils;
import edu.nwpu.store.web.base.BaseServlet;

@WebServlet("/OrderServlet")
public class OrderServlet extends BaseServlet {

	// saveOrder 将购物车中的信息以订单的形式保存
	public String saveOrder(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("OrderServlet");
		//确认用户登录状态
		User user = (User)request.getSession().getAttribute("loginUser");
		if (null == user) {
			request.setAttribute("msg", "请在登录之后下单！");
			return "jsp/info.jsp";
		}
		//获取购物车
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		//创建订单对象，为订单对象赋值
		Order order = new Order();
		order.setOid(UUIDUtils.getId());
		order.setOrderTime(new Date());
		order.setTotal(cart.getTotal());
		order.setState(1);//
		order.setUser(user);
		//遍历购物项的同时, 创建订单项, 为订单项赋值
		for (CartItem cartItem : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setItemid(UUIDUtils.getId());
			orderItem.setQuantity(cartItem.getNum());
			orderItem.setTotal(cartItem.getSubTotal());
			orderItem.setProduct(cartItem.getProduct());
			
			//设置当前的订单项属于哪个订单: 程序角度体现订单项和订单对应关系
			orderItem.setOrder(order);
			order.getList().add(orderItem);
		}
		//调用业务层功能：保存订单
		OrderService os = new OrderServiceImp();
		try {
			//将订单数据 用户的数据 订单下所有的订单项都传递到了service层
			os.saveOrder(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//清空购物车
		cart.clearCart();
		//将订单放入request
		request.setAttribute("order", order);
		//转发/jsp/order_info.jsp
		System.out.println("end1");
		return "/jsp/order_info.jsp";
	}
}

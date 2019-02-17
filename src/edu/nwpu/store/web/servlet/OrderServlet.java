package edu.nwpu.store.web.servlet;

import java.io.IOException;
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
import edu.nwpu.store.utils.PaymentUtil;
import edu.nwpu.store.utils.UUIDUtils;
import edu.nwpu.store.web.base.BaseServlet;

@WebServlet("/OrderServlet")
public class OrderServlet extends BaseServlet {

	// saveOrder 将购物车中的信息以订单的形式保存
	public String saveOrder(HttpServletRequest request, HttpServletResponse response) {
		// System.out.println("OrderServlet");
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
		// System.out.println("end1");
		return "/jsp/order_info.jsp";
	}

	public String findOrdersWithPage(HttpServletRequest request, HttpServletResponse response) {
		// 获取用户信息
		User user = (User) request.getSession().getAttribute("loginUser");
		// 获取当前页
		int curNum = Integer.parseInt(request.getParameter("num"));
		// 调用业务层功能：查询当前用户订单信息，返回PageModel
		OrderService os = new OrderServiceImp();
		// SELECT * FROM orders WHERE uid=? LIMIT ?,?
		// PageModel-pm: 1_分页参数 2_当前用户当前页的订单（集合）, 每笔订单上对应的订单项, 以及订单项对应的商品信息
		PageModel pm = null;
		try {
			pm = os.findMyOrdersWithPage(user, curNum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 将PageModel放入request
		request.setAttribute("page", pm);
		// 转发到/jsp/order_list.jsp
		return "/jsp/order_list.jsp";
	}

	// findOrderByOid
	public String findOrderByOid(HttpServletRequest request, HttpServletResponse response) {
		// 获取到订单的oid
		String oid = request.getParameter("oid");
		// 调用业务层功能：根据订单编号查询订单信息
		OrderService os = new OrderServiceImp();
		Order order = null;
		try {
			order = os.findOrderByOid(oid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 将订单放入request
		request.setAttribute("order", order);
		// 转发到/jsp/order_info.jsp
		return "/jsp/order_info.jsp";
	}

	// 易宝支付
	public String payOrder(HttpServletRequest request, HttpServletResponse response) {
		// 获取订单oid，收货人地址，姓名，电话，银行
		String oid = request.getParameter("oid");
		String address = request.getParameter("address");
		String name = request.getParameter("name");
		String telephone = request.getParameter("telephone");
		String pd_FrpId = request.getParameter("pd_FrpId");

		// 更新订单上收货人的地址，姓名，电话
		OrderService os = new OrderServiceImp();
		Order order = null;
		try {
			order = os.findOrderByOid(oid);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		order.setAddress(address);
		order.setName(name);
		order.setTelephone(telephone);
		order.setUser((User) request.getSession().getAttribute("loginUser"));
		try {
			os.updateOrder(order);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 向易宝支付发送参数
		// 把付款所需要的参数准备好:
		String p0_Cmd = "Buy";
		// 商户编号
		String p1_MerId = "10001126856";
		// 订单编号
		String p2_Order = oid;
		// 金额
		String p3_Amt = "0.01";
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		// 接受响应参数的Servlet
		String p8_Url = "http://localhost:8080/store_nwpu_v1/OrderServlet?method=callBack";
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		// 公司的秘钥
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";

		// 调用易宝的加密算法,对所有数据进行加密,返回电子签名(密文)
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
				p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);

		StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
		sb.append("p1_MerId=").append(p1_MerId).append("&");
		sb.append("p2_Order=").append(p2_Order).append("&");
		sb.append("p3_Amt=").append(p3_Amt).append("&");
		sb.append("p4_Cur=").append(p4_Cur).append("&");
		sb.append("p5_Pid=").append(p5_Pid).append("&");
		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		sb.append("p8_Url=").append(p8_Url).append("&");
		sb.append("p9_SAF=").append(p9_SAF).append("&");
		sb.append("pa_MP=").append(pa_MP).append("&");
		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		sb.append("hmac=").append(hmac);

		// System.out.println(sb.toString());

		// 使用重定向：
		try {
			response.sendRedirect(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	// 易宝反馈
	public String callBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 接收易宝支付响应回的数据
		// 验证请求来源和数据有效性
		// 阅读支付结果参数说明
		// System.out.println("==============================================");
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");

		// hmac
		String hmac = request.getParameter("hmac");
		// 利用本地密钥和加密算法 加密数据
		String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
		
		// 保证响应回的数据是合法的
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid,
				r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);
		if (isValid) {
			// 有效
			if (r9_BType.equals("1")) {
				// 浏览器重定向

				// 如果支付成功,更新订单状态
				OrderService os = new OrderServiceImp();
				Order order = os.findOrderByOid(r6_Order);
				order.setState(2);
				os.updateOrder(order);
				// 向request放入提示信息
				request.setAttribute("msg", "支付成功！订单号：" + r6_Order + "金额：" + r3_Amt);
				// 转发到/jsp/info.jsp
				return "/jsp/info.jsp";
			} else if (r9_BType.equals("2")) {
				// 修改订单状态:
				// 服务器点对点，来自于易宝的通知
				System.out.println("收到易宝通知，修改订单状态！");//
				// 回复给易宝success，如果不回复，易宝会一直通知
					response.getWriter().print("success");

			}

		} else {
			throw new RuntimeException("数据被篡改！");
		}

		return null;
	}
}

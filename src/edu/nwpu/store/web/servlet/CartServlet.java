package edu.nwpu.store.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.nwpu.store.domain.Cart;
import edu.nwpu.store.domain.CartItem;
import edu.nwpu.store.domain.Product;
import edu.nwpu.store.service.ProductService;
import edu.nwpu.store.service.serviceIml.ProductServiceImp;
import edu.nwpu.store.web.base.BaseServlet;

@WebServlet("/CartServlet")
public class CartServlet extends BaseServlet{
	private static final long serialVersionUID = 1L;

	//添加购物项到购物车
	public String addCartItemToCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//从Session中获取购物车
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if (null == cart) {
			//获取不到，创建购物车对象，放进Session
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		
		//获取到，直接使用即可
		
		//获取到商品id，数量
		String pid = request.getParameter("pid");
		int num = Integer.parseInt(request.getParameter("quantity"));
		//通过商品id，查询商品对象
		ProductService ps = new ProductServiceImp();
		Product product = ps.findProductByPid(pid);
		//获取到待购买的购物项
		CartItem cartItem = new CartItem();
		cartItem.setNum(num);
		cartItem.setProduct(product);
		//调用购物车上的方法
		cart.addCartItemToCart(cartItem);
		//重定向到/jsp/cart.jsp
		response.sendRedirect("/store_nwpu_v1/jsp/cart.jsp");
		return null;
	}

}

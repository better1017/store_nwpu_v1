package edu.nwpu.store.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

//两个属性，三个方法
public class Cart {
	// 个数不确定的购物项
	Map<String, CartItem> map = new HashMap<String, CartItem>();
	// 总计/积分
	private double total;

	// 添加购物项到购物车
	// 用户点击加入购物车，将商品id和数量发送到服务端，服务端根据id查找到商品信息
	// 有了商品信息Product对象和购买数量，当前的购物项就可以获取了
	public void addCartItemToCart(CartItem cartItem) {
		// 获取到正在向购物车中添加的商品的pid
		String pid = cartItem.getProduct().getPid();
		// 判断之前是否添加过
		// 没有，map.put(pid, CartItem);
		// 加过，获取到原先的数量加上本次要买的数量，就是一共要加入购物车的数量
		if (map.containsKey(pid)) {
			// 获取原先的购物项
			CartItem oldItem = map.get(pid);
			oldItem.setNum(oldItem.getNum() + cartItem.getNum());
		} else {
			map.put(pid, cartItem);
		}
	}

	// 移除购物项
	public void removeCartItem(String pid) {
		map.remove(pid);
	}

	// 清空购物车
	public void clearCart() {
		map.clear();
	}

	// 计算总计
	public double getTotal() {
		//先让总计清零
		total = 0;
		// 获取到map中所有的购物项
		Collection<CartItem> values = map.values();
		
		//遍历所有的购物项，小计相加
		for (CartItem cartItem : values) {
			total+=cartItem.getSubTotal();
		}
		
		return total;
	}
	
	//返回map中所有的值：判断是否为空、遍历map
	public Collection<CartItem> getCartItems() {
		return map.values();
	}
}

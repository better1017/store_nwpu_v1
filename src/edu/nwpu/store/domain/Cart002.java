package edu.nwpu.store.domain;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author 杨远林
 * @不用这个，用Cart
 */
//两个属性，三个方法
public class Cart002 {
	// 个数不确定的购物项
	private List<CartItem> list = new ArrayList<CartItem>();
	// 总计/积分
	private double total;

	// 添加购物项到购物车
	// 用户点击加入购物车，将商品id和数量发送到服务端，服务端根据id查找到商品信息
	// 有了商品信息Product对象和购买数量，当前的购物项就可以获取了
	public void addCartItemToCart(CartItem cartItem) {
		// 判断之前是否添加过
		// 没有，list.addz(CartItem);
		// 加过，获取到原先的数量加上本次要买的数量，就是一共要加入购物车的数量

		// 设置变量，默认false：没有买过
		boolean flag = false;

		CartItem oldItem = null;// 存储原先购物项
		for (CartItem cartItem2 : list) {
			if (cartItem.getProduct().getPid().equals(cartItem2.getProduct().getPid())) {
				flag = true;
				oldItem = cartItem2;
			}
		}

		if (flag == false) {
			// 没有加过购物车
			list.add(cartItem);
		} else {
			// 两个数量相加
			oldItem.setNum(oldItem.getNum() + cartItem.getNum());
		}
	}

	// 移除购物项
	// 用户点击移除购物项链接，可以将当前购物类别的商品id发送到服务端
	public void removeCartItem(String pid) {
		// 遍历list，看每个Cart Item上的product对象上的id是否和服务端获取到的pid相等，如果相等，删除当前的购物项
		for (CartItem cartItem : list) {
			if (cartItem.getProduct().getPid().equals(pid)) {
				/**
				 * 删除当前的cartItem
				 * 需要通过迭代器删除当前的购物项
				 * 太麻烦了
				 */
			}
		}
	}

	// 清空购物车
	public void clearCart() {
		list.clear();
	}
	
	
}

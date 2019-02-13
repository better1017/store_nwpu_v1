package edu.nwpu.store.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
	private String oid;
	private Date orderTime;
	private double total;
	private int state;
	private String address;
	private String name;
	private String telephone;

	// private String uid;
	// 1_程序对象与对象发生关系，而不是对象和对象的属性发生关系
	// 2_设计Order目的：让Order携带订单上的数据向Service，Dao传递，User对象可以携带更多的数据
	private User user;

	// 程序中体现了订单对象和订单项之间的关系
	// 我们在项目中的部分功能中是有类似的需求：查询订单的同时，还需要获取订单下所有的订单项
	private List<OrderItem> list = new ArrayList<OrderItem>();

	public Order() {
	}

	public Order(String oid, Date orderTime, double total, int state, String address, String name, String telephone,
			User user) {
		this.oid = oid;
		this.orderTime = orderTime;
		this.total = total;
		this.state = state;
		this.address = address;
		this.name = name;
		this.telephone = telephone;
		this.user = user;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "Order [oid=" + oid + ", orderTime=" + orderTime + ", total=" + total + ", state=" + state + ", address="
				+ address + ", name=" + name + ", telephone=" + telephone + ", user=" + user + "]";
	}

}

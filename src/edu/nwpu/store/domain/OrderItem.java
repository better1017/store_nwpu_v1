package edu.nwpu.store.domain;

public class OrderItem {
	private String itemid;
	private int quantity;
	private double total;

	// 1_对象对应对象
	// 2_product，order携带更多数据
	private Product product;
	private Order order;

	public OrderItem() {
	}

	public OrderItem(String itemid, int quantity, double total, Product product, Order order) {
		this.itemid = itemid;
		this.quantity = quantity;
		this.total = total;
		this.product = product;
		this.order = order;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "OrderItem [itemid=" + itemid + ", quantity=" + quantity + ", total=" + total + ", product=" + product
				+ ", order=" + order + "]";
	}

}

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<HTML>
<style>
.sel_btn{
    height: 21px;
    line-height: 21px;
    padding: 0 11px;
    background: #02bafa;
    border: 1px #26bbdb solid;
    border-radius: 3px;
    /*color: #fff;*/
    display: inline-block;
    text-decoration: none;
    font-size: 12px;
    outline: none;
}
</style>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
	</HEAD>
	<body>
		<br>
		<form id="Form1" name="Form1" action="${pageContext.request.contextPath}/user/list.jsp" method="post">
			<table cellSpacing="1" cellPadding="0" width="100%" align="center" bgColor="#f5fafe" border="0">
				<TBODY>
					<tr>
						<td class="ta_01" align="center" bgColor="#afd1f3">
							<strong>订单列表</strong>
						</TD>
					</tr>
					
					<tr>
						<td class="ta_01" align="center" bgColor="#f5fafe">
							<table cellspacing="0" cellpadding="1" rules="all"
								bordercolor="gray" border="1" id="DataGrid1"
								style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
								<tr
									style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

									<td align="center" width="5%">
										序号
									</td>
									<td align="center" width="20%">
										订单编号
									</td>
									<td align="center" width="5%">
										订单金额
									</td>
									<td align="center" width="5%">
										收货人
									</td>
									<td align="center" width="5%">
										订单操作
									</td>
									<td align="center" width="60%">
										订单详情
									</td>
								</tr>
								<c:forEach items="${allOrders }" var="o" varStatus="status">
									<tr onmouseover="this.style.backgroundColor = 'white'"
										onmouseout="this.style.backgroundColor = '#F5FAFE';">
										<td style="CURSOR: hand; HEIGHT: 22px" align="center"
											width="5%">
											${status.count }
										</td>
										<td style="CURSOR: hand; HEIGHT: 22px" align="center"
											width="20%">
											${o.oid }
										</td>
										<td style="CURSOR: hand; HEIGHT: 22px" align="center"
											width="5%">
											${o.total }
										</td>
										<td style="CURSOR: hand; HEIGHT: 22px" align="center"
											width="5%">
											${o.name }
										</td>
										<td style="CURSOR: hand; HEIGHT: 22px" align="center"
											width="5%">
												<c:if test="${o.state==1 }">
													<a class="sel_btn" href="/store_nwpu_v1/AdminOrderServlet?method=updateOrderByOid&oid=${o.oid }&state=2">付款</a>
												</c:if>
												<c:if test="${o.state==2 }">
													<a class="sel_btn" href="/store_nwpu_v1/AdminOrderServlet?method=updateOrderByOid&oid=${o.oid }&state=3">发货</a>
												</c:if>
												<c:if test="${o.state==3 }">
													<a class="sel_btn" href="/store_nwpu_v1/AdminOrderServlet?method=updateOrderByOid&oid=${o.oid }&state=4">完成</a>
												</c:if>
												<c:if test="${o.state==4 }">订单已完成</c:if>
										</td>
										<td align="center" style="HEIGHT: 22px" width="60%">
											<input type="button" id="${o.oid }" value="订单详情" class="myClass"/>
											
											<table border="1" width="100%">
												<!-- <tr>
												<th>商品</th>
												<th>名称</th>
												<th>单价</th>
												<th>数量</th>
												</tr>
												<tr>
												<td><img alt="商品图片" src="/store_nwpu_v1/products/1/c_0001.jpg" width="50px" /></td>
												<td>DDD</td>
												<td>DDD</td>
												<td>DDD</td>
												</tr> -->
											</table>
											
										</td>
									</tr>
								</c:forEach>
							</table>
						</td>
					</tr>
					<tr align="center">
						<td colspan="7">
							
						</td>
					</tr>
				</TBODY>
			</table>
		</form>
	</body>
<!-- JavaScript代码写到最后，保证页面分离 -->
<script type="text/javascript">
$(function() {
	$(".myClass").click(function() {
		//获取当前订单id
		var id = this.id;
		
		// 获取当前按钮的文字
		var txt = this.value;
		
		// 获取到当前元素的下一个对象,即table标签
		var $tb = $(this).next();// 前边有$符号的是jquery对象
		
		if(txt == "订单详情"){
			
			// 清空一下table中的数据
			$tb.html("");
			
			//向服务端发送Ajax请求
			var url = "/store_nwpu_v1/AdminOrderServlet";
			var obj = {"method":"findOrderByOidWithAjax", "id":id};
			
			$.post(url, obj, function(data) {
				// alert(data);
				
				var th = "<tr><th>商品</th><th>名称</th><th>单价</th><th>数量</th></tr>";
				$tb.append(th);
				
				// 利用jquery遍历响应到客户端的数据
				$.each(data, function(i, obj) {
					var td = "<tr><td><img alt='商品图片' src='/store_nwpu_v1/" + obj.product.pimage + "' width='50px' />"
						+ "</td><td>" + obj.product.pname + "</td><td>" + obj.product.shop_price + "</td><td>"
						+ obj.quantity + "</td></tr>";
					$tb.append(td);
				});
			}, "json");
			
			this.value = "关闭详情";
			
		} else {
			this.value = "订单详情";
			// 清空一下table中的数据
			$tb.html("");
		}		
	});
});
</script>
</HTML>


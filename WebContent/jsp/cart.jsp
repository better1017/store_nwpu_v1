﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>购物车</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>
		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
			
			.container .row div {
				/* position:relative;
	 float:left; */
			}
			
			font {
				color: #3164af;
				font-size: 18px;
				font-weight: normal;
				padding: 0 10px;
			}
		</style>
	</head>

	<body>
		<%@ include file="/jsp/header.jsp" %>
	<div class="container">
	
		<c:if test="${empty  cart.cartItems}">
			<div class="row">
				<div class="col-md-12">
					<h1>您的购物车中还没有图书呢~~~ 快去添加吧！</h1>
				</div>
			</div>
		</c:if>
		<c:if test="${not empty  cart.cartItems}">
		<div class="row">

			<div style="margin:0 auto; margin-top:10px;width:950px;">
				<strong style="font-size:16px;margin:5px 0;">订单详情</strong>
				<table class="table table-bordered">
					<tbody>
						<tr class="warning">
							<th>图片</th>
							<th>商品</th>
							<th>价格</th>
							<th>数量</th>
							<th>小计</th>
							<th>操作</th>
						</tr>
						<c:forEach items="${cart.cartItems }" var="item">
							<tr class="active">
								<td width="60" width="40%">
									<input type="hidden" name="id" value="22">
									<img src="${pageContext.request.contextPath}/${item.product.pimage }" width="70" height="60">
								</td>
								<td width="30%">
									<a target="_blank">${item.product.pname }</a>
								</td>
								<td width="20%">
									￥${item.product.shop_price }
								</td>
								<td width="10%">
									<input type="text" name="quantity" value="${item.num }" maxlength="4" size="10">
								</td>
								<td width="15%">
									<span class="subtotal">￥${item.subTotal }</span>
								</td>
								<td>
									<a href="javascript:;" class="delete">删除</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		</c:if>

		<div style="margin-right:130px;">
			<div style="text-align:right;">
				<em style="color:#ff6600;">
			登录后确认是否享有优惠&nbsp;&nbsp;
		</em> 赠送积分: <em style="color:#ff6600;">${cart.total }</em>&nbsp; 商品金额: <strong style="color:#ff6600;">￥${cart.total }元</strong>
			</div>
			<div style="text-align:right;margin-top:10px;margin-bottom:10px;">
				<a href="${pageContext.request.contextPath}/jsp/order_info.jsp" id="clear" class="clear">清空购物车</a>
				<a href="${pageContext.request.contextPath}/jsp/order_info.jsp">
					<%--提交表单 --%>
					<input type="submit" width="100" value="提交订单" name="submit" border="0" style="background: url('${pageContext.request.contextPath}/img/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
					height:35px;width:100px;color:white;">
				</a>
			</div>
		</div>
		
		
		
	</div>
		<%@ include file="/jsp/footer.jsp" %>
	</body>
</html>
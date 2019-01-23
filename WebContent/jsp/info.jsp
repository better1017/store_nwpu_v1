<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>关于我们</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"
	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"
	type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/style.css" type="text/css" />
</head>

<body>
		
	<div class="container">
	
		<%--包含导航条 --%>
		<%@include file="/jsp/header.jsp" %>
		
		<div class="container">
			<div class="main_con">

				<h3>提示信息：</h3>
				<hr />
				<p>
					<font color="red">${msg}</font>
				</p>
			</div>
		</div>
		
		<%--页脚 --%>
		<%@include file="footer.jsp" %>
		
	</div>

</body>

</html>
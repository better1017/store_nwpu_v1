<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>选择快递服务</title>
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
		</style>
	</head>

<body>

	<div style="margin-top:222px;margin-left:375px;">
		<strong>选择快递服务：</strong>
		<p>
			<br/>
			<input type="radio" name="express" value="a" checked="checked" />
			<img src="${pageContext.request.contextPath}/img/express_image/a.PNG" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="radio" name="express" value="b" />
			<img src="${pageContext.request.contextPath}/img/express_image/b.PNG" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="radio" name="express" value="c" />
			<img src="${pageContext.request.contextPath}/img/express_image/c.PNG" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="radio" name="express" value="d" />
			<img src="${pageContext.request.contextPath}/img/express_image/d.PNG" align="middle" />
			
			<br/>
			<input type="radio" name="express" value="e" />
			<img src="${pageContext.request.contextPath}/img/express_image/e.PNG" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="radio" name="express" value="f" />
			<img src="${pageContext.request.contextPath}/img/express_image/f.PNG" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="radio" name="express" value="g" />
			<img src="${pageContext.request.contextPath}/img/express_image/g.PNG" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="radio" name="express" value="h" />
			<img src="${pageContext.request.contextPath}/img/express_image/h.PNG" align="middle" />
			
			<br/>
			<input type="radio" name="express" value="i" />
			<img src="${pageContext.request.contextPath}/img/express_image/i.PNG" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="radio" name="express" value="j" />
			<img src="${pageContext.request.contextPath}/img/express_image/j.PNG" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="radio" name="express" value="k" />
			<img src="${pageContext.request.contextPath}/img/express_image/k.PNG" align="middle" />

		</p>
		<hr/>
		<p style="text-align:center;margin:auto;">
			<input type="button" value="确认"/>
		</p>
		<hr/>
	</div>
</body>
</html>
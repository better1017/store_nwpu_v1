<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>菜单</title>
<link href="${pageContext.request.contextPath}/css/left.css" rel="stylesheet" type="text/css"/>
<!-- 引入dtree的css文件 -->
<link rel="StyleSheet" href="${pageContext.request.contextPath}/css/dtree.css" type="text/css" />
</head>
<body>
<table width="100" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="12"></td>
  </tr>
</table>
<table width="100%" border="0">
  <tr>
    <td>
<div class="dtree">

	<a href="javascript: d.openAll();">展开所有</a> | <a href="javascript: d.closeAll();">关闭所有</a>
	<!-- 引入dtree的js文件 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/dtree.js"></script>
	<script type="text/javascript">
	
		d = new dTree('d');
		d.add('01',-1,'系统菜单树');
		
		d.add('0102','01','分类管理','','','mainFrame');
		//param1：当前结点id
		//param2：父节点id
		//param3：父节点上的文字描述
		//param4：跳转路径
		//param5：提示信息
		//param6：希望发生变化的frame的属性值
		d.add('010201','0102','分类管理','${pageContext.request.contextPath}/AdminCategoryServlet?method=findAllCats','','mainFrame');
		
		d.add('0104','01','商品管理');
		d.add('010401','0104','商品管理','${pageContext.request.contextPath}/AdminProductServlet?method=findAllProductsWithPage&num=1','','mainFrame');
		d.add('010402','0104','已下架商品管理','${pageContext.request.contextPath}/admin/product/pushDown_list.jsp','','mainFrame');
		
		d.add('0105','01','订单管理');
		d.add('010501','0105','订单管理','${pageContext.request.contextPath}/AdminOrderServlet?method=findOrders','','mainFrame');
		d.add('010502','0105','未付款的订单','${pageContext.request.contextPath}/AdminOrderServlet?method=findOrders&state=1','','mainFrame');
		d.add('010503','0105','已付款的订单','${pageContext.request.contextPath}/AdminOrderServlet?method=findOrders&state=2','','mainFrame');
		d.add('010504','0105','已发货的订单','${pageContext.request.contextPath}/AdminOrderServlet?method=findOrders&state=3','','mainFrame');
		d.add('010505','0105','已完成的订单','${pageContext.request.contextPath}/AdminOrderServlet?method=findOrders&state=4','','mainFrame');
		
		d.add('0106','01','顾客用户管理');
		d.add('010601','0106','顾客用户管理','${pageContext.request.contextPath}/admin/user/list.jsp','','mainFrame');
		
		d.add('0107','01','管理员用户管理');
		d.add('010701','0107','管理员用户管理','${pageContext.request.contextPath}/admin/user/list.jsp','','mainFrame');
		
		document.write(d);
		
	</script>
</div>	</td>
  </tr>
</table>
</body>
</html>

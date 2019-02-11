<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body> 
<%--分页显示的开始 --%>
    	<div style="text-align:center">
    		共${page.totalPageNum}页/第${page.currentPageNum}页
    		
    		<a href="${pageContext.request.contextPath}/${page.url}?num=1">首页</a>
    		
    		<c:if test="${page.currentPageNum != 1}">
    			<a href="${pageContext.request.contextPath}/${page.url}?num=${page.prePageNum}">上一页</a>
    		</c:if>
    		
    		
    		<%-- 获取开始结束页码
    			 ${page} *.getAttribute("page");  获取到pageModel对象
    			 ${page.startPage} 调用PageModel对象getStartPage()方法
    		--%> 
    		<c:forEach begin="${page.startPage}" end="${page.endPage}" var="pagenum">
    		   <c:if test="${page.currentPageNum==pagenum}">
    		   		${pagenum}
    		   </c:if>
    		   <c:if test="${page.currentPageNum!=pagenum}">
    		   		<a href="${pageContext.request.contextPath}/${page.url}?num=${pagenum}">${pagenum}</a>
    		   </c:if>
    		</c:forEach>
    		
    		<c:if test="${page.currentPageNum != page.totalPageNum}">
    			<a href="${pageContext.request.contextPath}/${page.url}?num=${page.nextPageNum}">下一页</a>
    		</c:if>
    		
    		
    		<a href="${pageContext.request.contextPath}/${page.url}?num=${page.totalPageNum}">末页</a>
    		
    		<input type="text" id="pagenum" name="pagenum" size="1"/>
    		<input type="button" value="前往" onclick="jump()" />
    		
    		<script type="text/javascript">
    			function jump(){
    				
    				//通过EL获取到总共页数,赋值给JS中变量
    				var totalpage = ${page.totalPageNum};
    				//获取到用户输入的内容
    				var pagenum = document.getElementById("pagenum").value;
    				//判断输入的是一个数字
    				var reg =/^[1-9][0-9]*$/;
    				if(!reg.test(pagenum)){
    					//不是一个有效数字
    					alert("请输入符合规定的数字");
    					return ;
    				}
    				//paseInt(pageNum):将用户输入的内容转换为int类型数字
    				//判断输入的数字不能大于总页数
    				if(parseInt(pagenum)>parseInt(totalpage)){
    					//超过了总页数
    					alert("不能大于总页数");
    					return;
    				}
    				//转向分页显示的Servlet
    				window.location.href="${pageContext.request.contextPath}/${page.url}?num="+pagenum;
    			}
    		</script>
    	</div>
    	<%--分页显示的结束--%>

</body>
</html>
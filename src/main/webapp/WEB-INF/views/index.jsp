<%@ page contentType="text/html; charset=UTF-8"%>
<%@include file="/WEB-INF/views/common/_header.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="container">
<c:if test="${not empty responseOk}">
	<div class="alert alert-success"><%=request.getAttribute("responseOk")%></div>
</c:if>
<c:if test="${not empty error}">
	<div class="alert alert-danger" role="alert"><%=request.getAttribute("error")%></div>
</c:if>

	<h2 style="color:#6a5acd">Les promos de la semaine !</h2>
	<ul class="articles">
		<%-- Iterate through articles ... --%>
		<c:forEach items="${articles}" var="item">
			<li>
				<a href="#"> 
					<span class="price"> <fmt:formatNumber type="currency" value="${item.price/100.0}" currencySymbol=" €" /></span>
					<img src="${item.img}" /><br /> ${item.name}<br />
				</a>
				<form action="cart/add.html" modelAttribute="art" method="post">
					<input class="d-none" style="width: 40px;" name="id"
						value="${item.id}" /> <input class="d-none" type="number"
						name="qty" value=1 placeholder="quantité" />
					
					<input class="btn btn-primary" style="height: 30px;" id="submit" type="submit" value="Add" />
					 
					<%--<a onclick="Submit()"><span class="glyphicon glyphicon-plus-sign addToCart" data-ref="120574"></span></a>--%>
				</form></li>
				
		</c:forEach>
		
	</ul>
</div>
<%
jsList.add("cart.js");
%>

<script type="text/javascript">
	function Submit() {
		document.getElementById("submit").click();
	}
</script>
<%@include file="/WEB-INF/views/common/_footer.jsp" %>


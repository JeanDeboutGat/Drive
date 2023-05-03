<%@include file="/WEB-INF/views/common/_header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="container">
	<h2 style="color:#6a5acd">Details de la commande :</h2>
	<c:if test="${not empty order_details}">
		<table class="table">
			<thead class="thead-light">
				<tr>
					<th scope="col"></th>
					<th scope="col">Article</th>
					<th scope="col">Price</th>
					<th scope="col">Quantity</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${order_details}" var="item">
					<a href="">
						<tr>
							<td><img src="${item.article.img}" height="30px" /></td>
							<td>${item.article.name}</td>
							<td><fmt:formatNumber type="currency"
									value="${item.article.price/100.0}" currencySymbol=" €" /></td>
							<td>${item.quantity}</td>
						</tr>
					</a>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</div>

<%@include file="/WEB-INF/views/common/_footer.jsp" %>
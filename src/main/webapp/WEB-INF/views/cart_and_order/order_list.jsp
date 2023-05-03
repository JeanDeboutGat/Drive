<%@include file="/WEB-INF/views/common/_header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="container">
	<h2 style="color:#6a5acd">Liste des commandes</h2>
	<c:if test="${not empty orders}">
		<table class="table">
			<thead class="thead-light">
				<tr>
					<th scope="col">N°</th>
					<th scope="col">Date</th>
					<th scope="col">Amount</th>
					<th scope="col">Status</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${orders}" var="item">
					<tr
						onclick="document.location='/drive/order/ofCustomer/${user.id}/detailsOrder/${item.id}.html'"
						style="cursor: pointer" class="clickable-row">
						<td>${item.id}</td>
						<td><fmt:formatDate value="${item.createdOn}"
								pattern="d/M/YY" /></td>
						<td><fmt:formatNumber type="currency"
								value="${item.amount/100.0}" currencySymbol=" €" /></span></td>
						<td>${item.currentStatus}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
	<c:if test="${empty orders}">
AUCUNE COMMANDE
</c:if>
</div>

<%@include file="/WEB-INF/views/common/_footer.jsp" %>

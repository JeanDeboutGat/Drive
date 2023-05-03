<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@include file="/WEB-INF/views/common/_header.jsp" %>
<div class="container">
	<h2 style="color:#6a5acd">Détails panier</h2>
	<c:if test="${not empty cartmap.articlesMap}">

		<table class="table">
			<thead class="thead-light">
				<tr>
					<th scope="col">Product</th>
					<th scope="col">Price</th>
					<th scope="col">Qty</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${cartmap.articlesMap}" var="item">
					<tr>
						<td>${item.key.name}</td>
						<td><fmt:formatNumber type="currency"
								value="${item.key.price/100.0}" currencySymbol=" €" /></span></td>
						<td>${item.value}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
	<c:if test="${empty cartmap.articlesMap}">
		<span style="text-aligh: center">Aucun article</span>
	</c:if>
	<c:if test="${not empty cartmap.articlesMap}">
		<a class="btn btn-primary" href="validate.html">Commander</a>
	</c:if>
</div>
<%@include file="/WEB-INF/views/common/_footer.jsp" %>
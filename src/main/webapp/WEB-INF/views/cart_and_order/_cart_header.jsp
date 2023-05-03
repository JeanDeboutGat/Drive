<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<pre>
	<c:if test="${not empty cartmap.articlesMap}">
		<table class="table">
  			<tbody>
  				<c:forEach items="${cartmap.articlesMap}" var="item">
    				<tr>
    					<td>${item.key.name}</td>
      					<td><fmt:formatNumber type="currency" value="${item.key.price/100.0}" currencySymbol=" €"/></span></td>
      					<td>x ${item.value}</td>
    				</tr>
    			</c:forEach>
  			</tbody>
		</table>
	</c:if>
	<c:if test="${empty cartmap.articlesMap}">
		<span style="text-aligh:center">Aucun article</span>
	</c:if>
</pre>
<a class="btn btn-primary" href="/drive/cart/validate.html">Commander</a>


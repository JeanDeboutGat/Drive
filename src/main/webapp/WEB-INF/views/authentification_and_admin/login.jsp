<%@ page contentType="text/html; charset=UTF-8" %>
<%@include file="/WEB-INF/views/common/_header.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty error}">
	<div class="alert alert-danger" role="alert"><%= request.getAttribute("error") %></div>
</c:if>
<c:if test="${not empty registerOk}">
	<div role="alert">You account is successfully created, you can now connect.</div>
</c:if>
<div class="centerdiv m-t-lg">
	<form action="login.html" method="post">
		<!-- Email input -->
		<div class="form-outline mb-4">
			<label class="form-label" for="email">Email address</label>
			<input type="email" id="email" class="form-control" name="email" />
		</div>

		<!-- Password input -->
		<div class="form-outline mb-4">
			<label class="form-label" for="password">Password</label>
			<input type="password" id="password" class="form-control" name="password" />
		</div>

		<!-- Submit button -->
		<button type="submit" class="btn btn-primary btn-block mb-4" style="margin-top: 10px; margin-bottom: 10px">Sign in</button>

		<!-- Register buttons -->
		<div class="text-center">
			<p>Not a member? <a href="/drive/register.html">Register</a></p>
		</div>
	</form>
</div>

<%@include file="/WEB-INF/views/common/_footer.jsp" %>
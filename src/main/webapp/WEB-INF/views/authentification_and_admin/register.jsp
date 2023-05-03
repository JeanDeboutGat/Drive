<%@include file="/WEB-INF/views/common/_header.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">

    <form class="form-signin" action="register.html" method="POST">
        
        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert"><%= request.getAttribute("error") %></div>
        </c:if>
        <c:if test="${not empty creationOk}">
            <div role="alert">To activate your account, please check your emails and click on the activate link.</div>
        </c:if>

        <h2 class="form-signin-heading">
            <span class="glyphicon glyphicon-log-in" aria-hidden="true"></span>
            Register Now !
        </h2>

        <div class="row form-outline m-b-sm">
            <div class="col-sm-6">
                <label for="firstname" class="form-label">Firstname</label>
                <input id="firstname" class="form-control" name="firstname" required autofocus type="text">
            </div>
            <div class="col-sm-6">
                <label for="lastname" class="form-label">Lastname</label>
                <input id="lastname" class="form-control" name="lastname" required autofocus type="text">
            </div>
        </div>

        <div class="row form-outline m-b-sm">
            <div class="col-sm-6">
                <label for="email" class="form-label">Email address</label>
                <input id="email" class="form-control" name="email" required autofocus type="email">
            </div>
            <div class="col-sm-6">
                <label for="address" class="form-label">Address</label>
                <input id="address" class="form-control" name="address" required autofocus type="email">
            </div>
        </div>

        <div class="row form-outline m-b-sm">
            <div class="col-sm-6">
                <label for="password" class="form-label">Password</label>
                <input id="password" class="form-control" name="password" required autofocus type="password" title="Must contain at least one  number and one uppercase and lowercase letter, and at least 8 or more characters" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$">
            </div>
            <div class="col-sm-6">
                <label for="passwordConfirm" class="form-label">Confirm Password</label>
                <input id="passwordConfirm" class="form-control" name="passwordConfirm" required autofocus type="password">
            </div>
        </div>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Register !</button>
    </form>

</div>

<%@include file="/WEB-INF/views/common/_footer.jsp" %>
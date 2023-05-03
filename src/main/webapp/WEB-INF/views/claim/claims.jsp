<%@ include file="/WEB-INF/views/common/_header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:useBean id="claims" scope="request" type="java.util.List<fr.eservices.drive.model.Claim>"/>
<jsp:useBean id="user" scope="session" type="fr.eservices.drive.model.User"/>


<div class="container">
    <!-- New claim part -->
    <c:if test="${user.role == Role.USER}">
        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert"><c:out value="${requestScope.get('error')}"/></div>
        </c:if>
        <button type="button" class="btn btn-primary btn-lg btn-block centerdiv m-t-md" data-toggle="modal" data-target="#claimForm">
            CONTACT US
        </button>

        <div class="modal fade" id="claimForm" tabindex="-1" role="dialog" aria-labelledby="claimForm"
             aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="panel-heading" style="background-color: #e7e7e7;">
                        <h5 class="text-center font-weight-bold">Please fill the form</h5>
                    </div>

                    <form action="claims.html" METHOD="POST">
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="recipient-name" class="col-form-label">Order Number:</label>
                                <select class="form-control" name="orderId" required>
                                	<option value="">--Please choose an option--</option>
                                	<c:forEach items="${ordersId}" var="order">
                                		<option value="${order}">${order}</option>
                                	</c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="object-text" class="col-form-label">Claim Object:</label>
                                <textarea class="form-control" id="object-text" name="object" rows="5"
                                          cols="30" required></textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-primary">Send</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </c:if>
    <br>
    <!-- Claims list part -->
    <div class="panel">
        <div class="panel-heading" style="background-color: #e7e7e7;">
            <h5>RECENT CLAIMS</h5>
        </div>
        <div class="panel-body">
            <c:choose>
                <c:when test="${not empty claims}">
                    <div class="list-group">
                        <c:forEach var="claim" items="${claims}">
                            <a href="claims/${claim.orderId}.html" class="list-group-item list-group-item-action">
                                <c:out value="${claim.object}"/> <br>
                            </a>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <p>No claim yet</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/views/common/_footer.jsp" %>

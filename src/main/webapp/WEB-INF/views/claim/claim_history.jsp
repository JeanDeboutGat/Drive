<%@ include file="/WEB-INF/views/common/_header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="fr.eservices.drive.util.ClaimStatus" %>
<jsp:useBean id="claim" scope="request" type="fr.eservices.drive.model.Claim"/>
<c:set var="STATUS_OPEN" value="<%=ClaimStatus.OPEN%>"/>
<c:set var="USER_STATUS" value="<%=Role.USER%>"/>
<c:set var="ADMIN_STATUS" value="<%=Role.ADMIN%>"/>
<c:set var="SUPERADMIN_STATUS" value="<%=Role.SUPERADMIN%>"/>

<div class="container">
    <c:if test="${not empty error}">
    <div class="alert alert-danger" role="alert"><%= request.getAttribute("error") %>
    </div>
    </c:if>

    <!-- Claim object display -->
    <div class="card" style="margin: 2em 0">
        <div class="card-header">
            Claim Object For Order N° ${claim.orderId}
        </div>
        <div class="card-body" style="padding: 1em ">
            <p class="card-text"><c:out value="${claim.object}"/></p>
            <c:if test="${user.role == USER_STATUS && claim.status == STATUS_OPEN}">
                <button type="button" Class="btn btn-primary" data-toggle="modal"
                        data-target="#objectModificationForm">
                    Modify
                </button>
            </c:if>
        </div>
    </div>
    <!-- Claim modification form -->
    <div class="modal fade" id="objectModificationForm" tabindex="-1" role="dialog"
         aria-labelledby="objectModificationForm" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="panel-heading" style="background-color: #e7e7e7;">
                        <h5>Modify The Claim</h5>
                    </div>
                </div>
                <form action="${claim.orderId}.html" METHOD="POST">
                    <div class="modal-body">
                        <div class="form-group">
                        <textarea class="form-control" id="object-modification-text" name="modifiedObject" rows="5"
                                  cols="30" required><c:out value="${claim.object.trim()}"/>
                        </textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                        <button type="submit" class="btn btn-primary">Validate modification</button>
                    </div>
                </form>

            </div>
        </div>
    </div>
    <div class="separator" style="height: 40px"></div>

    <!-- List of message exchanged -->
    <div class="panel">
        <div class="panel-heading" style="background-color: #e7e7e7;">
            <h5>Recent Messages</h5>
        </div>
        <div class="panel-body">

            <c:if test="${not empty claim && not empty claim.messages}">
                <div class="list-group">
                    <c:forEach var="message" items="${claim.messages}">

                        <c:if test="${message.source == USER_STATUS}">
                            <div class="message-data text-right">
                                <small><fmt:formatDate type="both" dateStyle="short" timeStyle="short"
                                                       value="${message.date}"/> </small>
                                <i class="material-icons">person</i>
                            </div>
                            <div class="card" style="background-color: #efefef; min-height: 3em;padding: 1em">
                                    ${message.content}
                            </div>
                        </c:if>
                        <c:if test="${message.source == ADMIN_STATUS || message.source == SUPERADMIN_STATUS}">
                            <div class="text-left">
                                <i class="material-icons">support_agent</i>
                                <small><fmt:formatDate type="both" dateStyle="short" timeStyle="short"
                                                       value="${message.date}"/></small>
                            </div>
                            <span class="card" style="background-color: #e8f1f3;min-height: 3em; padding: 1em">
                                    ${message.content}
                            </span>
                        </c:if>
                    </c:forEach>
                </div>
            </c:if>

            <c:if test="${empty claim || empty claim.messages}">
                <p>No messages exchanged yet</p>
            </c:if>
        </div>
    </div>
    <div class="separator" style="height: 10px"></div>

    <!-- Add a message -->
    <form action="${ctxPath}/drive/claims/${THEID}/message.html" METHOD="POST" style="margin-bottom: 2em">
        <div class="form-group">
        <textarea class="form-control" id="message-text" name="messageContent" rows="5"
                  cols="30" placeholder="Add a new message" required></textarea>
        </div>
        <button type="submit" class="btn btn-primary">Send a message</button>
    </form>
    <c:if test="${user.role != USER_STATUS}">
   	    <a href="/drive/claims/${THEID}/close.html" class="btn btn-danger">Close the claim</a>
    </c:if>

    <%@include file="/WEB-INF/views/common/_footer.jsp" %>

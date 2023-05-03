<%@ page import="fr.eservices.drive.util.Role" %>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="USER" value="<%=Role.USER%>"/>
<c:set var="ADMIN" value="<%=Role.ADMIN%>"/>

<html lang="en">
<% String ctxPath = request.getContextPath();
java.util.List<String> jsList = new java.util.ArrayList<>(); 
%>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <title>My Web Drive</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.2/css/bootstrap.min.css" integrity="sha384-y3tfxAZXuh4HwSYylfB+J125MxIs6mR5FOHamPBG064zB+AFeWH94NdvaCBm8qnd" crossorigin="anonymous">
  <link href="<%= ctxPath %>/css/bootstrap.min.css" rel="stylesheet"/>
  <link href="<%= ctxPath %>/css/ie10-viewport-bug-workaround.css" rel="stylesheet"/>
  <link href="<%= ctxPath %>/css/main.css" rel="stylesheet"/>
  <link href="<%= ctxPath %>/css/spacing.css" rel="stylesheet"/>
  <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>
<body>

<!-- Fixed navbar -->
<header class="navbar navbar-default navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="/drive/index.html"><span class="glyphicon glyphicon-barcode"></span>
       My Web Drive</a>
    </div>
    <div id="navbar" class="navbar-collapse collapse">
      <ul class="nav navbar-nav">
        <li class="active"><a href="/drive/index.html">Home</a></li>
        <li><a href="#about">About</a></li>
        <c:if test="${!empty sessionScope.user}">
        <li><a href="/drive/claims.html">Contact</a></li>
        </c:if>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
          	<span class="glyphicon glyphicon-user"></span> Mon compte <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <c:if test="${empty sessionScope.user}">
              <li><a href="/drive/register.html"><span class="glyphicon glyphicon-log-in"></span> Inscription</a></li>
              <li><a href="/drive/login.html"><span class="glyphicon glyphicon-log-in"></span> Connexion</a></li>
            </c:if>
            <c:if test="${!empty sessionScope.user}">
           	  <li><a href="/drive/order/ofCustomer/${user.id}.html"><span class="fa fa-history"></span> Voir l'historique de mes commandes </a></li>
              <li><a href="/drive/logout.html"><span class="glyphicon glyphicon-off"></span> Déconnexion</a></li>
            </c:if>
          </ul>
        </li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
          	<span class="glyphicon glyphicon-shopping-cart"></span> Panier <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li>
            <jsp:include page="/WEB-INF/views/cart_and_order/_cart_header.jsp">
            	<jsp:param name="cart" value="${cartmap}"/>
            	<jsp:param name="art" value="${idcart}"/>
            	<jsp:param name="art" value="${art}"/>
            </jsp:include>
            </br>
            <a class="btn btn-primary" href="/drive/cart/Cart.html">Voir details panier</a>
            </li>
          </ul>
        </li>
        <c:if test="${!empty sessionScope.user && sessionScope.user.role != USER}">
          <li class="">
            <a href="/drive/admin.html"><span class="glyphicon glyphicon-user"></span>Panel Admin</a>
          </li>
        </c:if>
      </ul>
    </div><!--/.nav-collapse -->
  </div>
</header>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${not empty language ? language : pageContext.request.locale.language}"/>
<fmt:setBundle basename="localization.text" />
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title><fmt:message key="login"/></title>
	<link href="<c:url value="/assets/css/style.css" />" rel="stylesheet">
</head>
<body >
	<jsp:include page="header_fragment.jsp"></jsp:include>
	<form class = "form" method = "post" action = "/Airline/login">
        <section class="container one">
          
          <p class = "center"><label for="email"><fmt:message key="email"/></label>
            <input type="email" name="email" pattern="^[-\w.]+@([A-z0-9][-A-z0-9]+\.)+[A-z]{2,4}$" required
            	value="${email}" ></p>
          
          <p class = "center"><label for="password"><fmt:message key="password"/></label>
            <input type="password" name="password" pattern="^[а-яА-ЯёЁіІїЇa-zA-Z0-9]+$" required></p>
            
          <p class = "red">${error_message}</p>
        </section>
        
        <section class="container two">
        	<button type="submit"><fmt:message key="login"/></button>
        </section>
      </form>
</body>
</html>
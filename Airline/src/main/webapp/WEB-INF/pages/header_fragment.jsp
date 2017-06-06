<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${not empty language ? language : pageContext.request.locale.language}"/>
<fmt:setBundle basename="localization.text" />
<div class="header menu">
	<div class="left-float ">
		<div class="left-float">
			<a href="/Airline" class="margin-right"><img src="<c:url value="/assets/images/small_logo.jpg"/>"></a>
		</div>
		<div class="right-float margin-top class">
			<a href="/Airline/flights" class = "margin-right"><fmt:message key="flights"/></a>
			<c:if test = "${sessionScope.user.role == 'admin'}">
				<a href="/Airline/admin/users" class = "margin-right"><fmt:message key="users"/></a>
			</c:if>
		</div>
	</div>
	<div class="right-float margin-top">
		<a href="/Airline/change_lang?lang=en&link=${requestScope['javax.servlet.forward.request_uri']}&parameters=${pageContext.request.queryString}">eng</a><span> | </span> 
		<a href="/Airline/change_lang?lang=ru&link=${requestScope['javax.servlet.forward.request_uri']}&parameters=${pageContext.request.queryString}">rus</a><span> | </span> 
		<a href="/Airline/change_lang?lang=uk&link=${requestScope['javax.servlet.forward.request_uri']}&parameters=${pageContext.request.queryString}" class="margin-right">ukr</a> 
		<c:choose>
			<c:when test="${user == null}">
				<a href="/Airline/login_page" class="margin-right"> <fmt:message key="login"/></a> 
			</c:when>
			<c:otherwise>
				<a href="/Airline/user_info" class="margin-right"> ${sessionScope.user.firstName}</a> 
			</c:otherwise>
		</c:choose>	
		<c:choose>
			<c:when test="${user == null}">
				<a href="/Airline/registration_page"> <fmt:message key="sign_up"/></a>
			</c:when> 
			<c:otherwise>
				<a href="/Airline/logout"> <fmt:message key="logout"/></a>
			</c:otherwise>
		</c:choose>	
	</div>
</div>
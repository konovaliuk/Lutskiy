<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='/WEB-INF/currentDate.tld' prefix = 'tag'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${not empty language ? language : pageContext.request.locale.language}"/>
<fmt:setBundle basename="localization.text" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="element/html; charset=UTF-8">
<title><fmt:message key="flight"/></title>
<link href="<c:url value="/assets/css/style.css"/>" rel="stylesheet">
</head>
<body>
	<jsp:include page="header_fragment.jsp"></jsp:include>
	
		<form class = "form flight-util-form" method="post" 
			<c:if test = "${flight == null}"> 
				action="/Airline/admin/create_flight"
			</c:if> 
			<c:if test = "${flight != null}">
				action="/Airline/admin/save_flight"
			</c:if>
		>
		<input type = "hidden" name = "id" value = "${flight.id}"/>
		<p><label for="departure"><fmt:message key="departure"/></label>
            <input type="text" name="departure" required value="${flight.departure}">
        </p>
    	<p><label for="destination"><fmt:message key="destination"/></label>
            <input type="text" name="destination" required value="${flight.destination}">
        </p>
        <p><label for="date"><fmt:message key="date"/></label>
            <input type="date" name="date" required value="${flight.date}" 
            	<tag:currentDate var="currentDate"/>
				min="${currentDate}">
        </p>
        <c:if test = "${flight == null}">
        	<button type="submit"><fmt:message key="create"/></button>
        </c:if>
        <c:if test = "${flight != null}">
        	<div class = "inline-center">
        		<button type="submit" class = "btn-line"><fmt:message key="save"/></button>
        		<a href="/Airline/admin/delete_flight?id=${flight.id}" class="form-button"> <fmt:message key="delete"/></a>
        	</div>
        </c:if>
	</form>
</body>
</html>
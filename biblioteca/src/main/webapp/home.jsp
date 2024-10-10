<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<jsp:directive.include file="/includes/includefile.jspf"/>
</head>
<body>

	<div class="container">
		<div class="header"></div>
		<div class="menu">
			<jsp:directive.include file="/WEB-INF/menu.jspf"/>
		</div>
	</div>
	<fmt:formatNumber></fmt:formatNumber>
</body>
</html>
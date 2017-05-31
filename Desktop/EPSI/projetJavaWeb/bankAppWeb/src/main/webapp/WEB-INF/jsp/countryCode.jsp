<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mon compte</title>
</head>
<body>

	<section>
		<c:out value="${code.code}"/>&nbsp;: coucou
	</section>
	
	<nav>
		<ul>
			<li><a href="<c:url value="/"/>">Affichage code</a></li>
		</ul>
	</nav>

</body>
</html>
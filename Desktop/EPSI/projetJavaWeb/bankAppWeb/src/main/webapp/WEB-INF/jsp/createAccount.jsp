<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Création d'un compte</title>
</head>
<body>

<form action="<c:url value="/accounts"/>" method="POST">
	<label>Numéro du compte</label>
	<input name="code" value="<c:out value="${param['code']}"/>"><br>
	<button type="submit">Créer</button>
</form>

</body>
</html>
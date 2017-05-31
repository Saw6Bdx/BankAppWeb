<%-- 
    Document   : index
    Created on : 29 mai 2017, 09:24:55
    Author     : Guest
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Categories</title>
    </head>
    
    <body>
        <h1>Which category do you want to read?</h1>
        
        <%--<c:choose>
            <c:when test="${error eq 'invalid.amount.format'}">
                The first balance must be a number&nbsp;!<br><br>
            </c:when>
            <c:when test="${error eq 'account.already.exists'}">
                This account already exists&nbsp;!<br><br>
            </c:when>
        </c:choose>--%>
   
        <form action="<c:url value="/category"/>" method="post">
            <label>Category name</label><br>
            <input name="categoryLabel" value="<c:out value="${param['categoryLabel']}"/>"><br><br>
            <button type="submit">Create</button>
        </form>
    </body>
</html>

<%-- 
    Document   : BudgetCategory
    Created on : 29 mai 2017, 10:42:36
    Author     : Mary
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Categories</title>
    </head>
    <body>
        <h1>categories</h1>
        <form action="<c:url value="/category"/>" method="post">
            <label>Which category do you want to read?</label><br>
            <input name="categoryLabel" value="<c:out value="${param['categoryLabel']}"/>"><br><br>
            <button type="submit">Submit</button>
        </form>
        <table>
            <c:forEach items="${listCategories}" var="item">
                <tr>
                    <td><c:out value="${item}"/></td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>

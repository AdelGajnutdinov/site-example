<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Адель
  Date: 25/03/18
  Time: 16:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<span style="color:${cookie.color.value}">Hello!</span>
<form method="post" action="<c:url value="/home"/>">
    <label for="color">
        <select name="color" id="color">
            <option value="red">Red</option>
            <option value="black">Black</option>
            <option value="white">White</option>
        </select>
    </label>
    <input type="submit" value="Color send">
</form>
</body>
</html>

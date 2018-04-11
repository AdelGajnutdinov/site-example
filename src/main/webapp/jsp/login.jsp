<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link href="<c:url value="/css/styles.css"/>" rel="stylesheet" type="text/css">
</head>
<body>
<div class="form-style-2">
    <div class="form-style-2-heading">
        Please login!
    </div>
    <form method="post" action="/login">
        <label for="name"> Name
            <input class="input-field" type="text" id="name" name="name">
        </label>
        <label for="password"> Password
            <input class="input-field" type="password" id="password" name="password">
        </label>
        <input class="input-field" type="submit" value="Login!">
    </form>
</div>
</body>
</html>
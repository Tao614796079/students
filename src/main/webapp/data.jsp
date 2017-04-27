<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"
         pageEncoding="UTF-8" %>
<html>
<head><title>正在跳转</title></head>
<body>
<button onclick="window.location.href='${pageContext.request.contextPath}/add_batch'">批量插入数据</button>
<button onclick="window.location.href='${pageContext.request.contextPath}/flushDB'">清除数据</button>
</body>
</html>


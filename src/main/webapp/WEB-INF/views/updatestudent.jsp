<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta charset="utf-8">
    <title>修改信息</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/form.js"></script>
</head>
<body>
<h1 class="text-center">修改信息</h1>
<div class="row">
    <div class="col-lg-3"></div>
    <div class="col-lg-6">
        <form action="${pageContext.request.contextPath}/updateStudent" method="post" id="form">
            <h4>学号：</h4>
            <input type="hidden" name="cur_page" value="${cur_page}">
            <input type="hidden" name="id" value="${student.id}">
            <input type="text" class="form-control" id="id" value="${student.id}" disabled="true ">
            <h4>姓名：</h4>
            <input type="text" class="form-control" id="name" name="name" value="${student.name}">
            <h4>平均成绩：</h4>
            <input type="text" class="form-control" id="avgscore" name="avgscore" value="${student.avgscore}">

            <h4>出生日期：</h4>
            <input type="date" class="form-control" id="birthday" name="birthday"
                   value="<fmt:formatDate value='${student.birthday}' pattern='yyyy-MM-dd' />"/>

            <div class="form-group">
                <h4>备注：</h4>
                <textarea class="form-control" rows="4" id="description"
                          name="description">${student.description}</textarea>
            </div>
            <br>
            <div class="btn-group pull-right">
                <button type="button" class="btn btn-success" onclick="sub();">提交</button>
                <button type="reset" class="btn btn-danger">重置</button>
            </div>
        </form>
    </div>
    <div class="col-lg-3"></div>
</div>
</body>
</html>

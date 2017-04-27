<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta charset="utf-8">
    <title>学生列表</title>
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div>
    <h1 class="text-center">学生信息表</h1>
    <br>
</div>
<div style="padding-left: 2%;padding-right: 2%;">
    <button type="button" class="btn btn-default pull-right"
            onclick="window.location.href='${pageContext.request.contextPath}/toAdd'">添加学生信息
    </button>
    <br><br>
    <table class="table-bordered text-center" width="100%">
        <tr>
            <th width="10%" class="text-center">学号</th>
            <th width="15%" class="text-center">姓名</th>
            <th width="10%" class="text-center">平均分</th>
            <th width="15%" class="text-center">出生日期</th>
            <th width="40%" class="text-center">备注</th>
            <th width="10%" class="text-center">操作</th>
        </tr>
        <c:forEach items="${studentList}" var="bean">
            <tr>
                <td>${bean.id}</td>
                <td>${bean.name}</td>
                <td>${bean.avgscore}</td>
                <td><fmt:formatDate value="${bean.birthday}" pattern="yyyy-MM-dd"/></td>
                <td>${bean.description}</td>
                <td><a href="${pageContext.request.contextPath}/delete?id=${bean.id}&cur_page=${cur_page}">删除</a>/<a
                        href="${pageContext.request.contextPath}/toUpdate?id=${bean.id}&cur_page=${cur_page}">修改</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <div class="text-center">
        <button type="button" class="btn btn-default btn-inverse"
                onclick="last_page()"><
            <上一页
        </button>
        <span>&nbsp;&nbsp;${cur_page}/${total_page}&nbsp;&nbsp;</span>
        <button type="button" class="btn btn-default btn-inverse"
                onclick="next_page()">下一页>>
        </button>

    </div>

</div>
<script>
    function next_page() {
        var cur = ${cur_page};
        var count = ${total_page};
        if (cur < count) {
            window.location.href = "${pageContext.request.contextPath}/list?cur_page=" + (cur + 1);
        } else {
            alert("已经是最后一页了！");
        }
    }
    function last_page() {
        var cur = ${cur_page};
        if (cur > 1) {
            window.location.href = "${pageContext.request.contextPath}/list?cur_page=" + (cur - 1);
        } else {
            alert("已经是第一页了！");
        }
    }
</script>
</body>
</html>

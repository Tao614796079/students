/**
 * Created by Administrator on 2017/4/27.
 */
function sub() {
    var id = document.getElementById("id");
    var name = document.getElementById("name");
    var avgscore = document.getElementById("avgscore");
    var birthday = document.getElementById("birthday");
    if (trim(id.value) == null || trim(id.value) == "") {
        alert("请输入学号");
        id.focus();
        return;
    }
    if (trim(name.value) == null || trim(name.value) == "") {
        alert("请输入姓名");
        name.focus();
        return;
    }
    if (trim(avgscore.value) == null || trim(avgscore.value) == "") {
        alert("请输入成绩");
        avgscore.focus();
        return;
    }
    if (trim(birthday.value) == null || trim(birthday.value) == "") {
        alert("请选择出生日期");
        birthday.focus();
        return;
    }
    document.getElementById("form").submit();
}
function trim(str) { //删除左右两端的空格
    return str.replace(/(^\s*)|(\s*$)/g, "");
}
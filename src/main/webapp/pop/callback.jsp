<%--
  Created by IntelliJ IDEA.
  User: lscm
  Date: 2016/8/31
  Time: 20:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<script>
    var div = document.getElementById("haiyunssp");
    if(div!=undefined){
        var astript = div.getElementsByTagName("a")[0];
        if(astript!=undefined) div.removeChild(astript);
    }
    var b5m_l = 100;
    if(level != undefined){
        b5m_l = level;
    }else if(parent.window.level!=undefined){
        b5m_l = parent.window.level++;
    }else if(parent.window.parent.window.level != undefined) {
        b5m_l = parent.window.parent.window.level;
    }
    b5m_l++;
    b5m_FillSlot(b5m_l);
</script>
</body>
</html>

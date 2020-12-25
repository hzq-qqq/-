<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/12/12
  Time: 21:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <link rel="stylesheet" href="/css/file.css">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>文件目录</title>
    <script>

    </script>
</head>
<body>
<div id="yp_user">
    用户信息:${sessionScope.user.username}
</div>
<div id="yp_fol">
    <table>
        <tr>
            <th>文件夹名称</th>
        </tr>
        <c:forEach begin="0" end="${sessionScope.tpFolList.size()-1}" var="i" step="1">
            <tr>
                <td>
                    <a href="findDoc?fid=${sessionScope.tpFolList.get(i).fid}">${sessionScope.tpFolList.get(i).fname}</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<div id="yp_doc">
    <table>
        <tr>
            <th></th>
            <th><h2>文件名</h2></th>
            <th><h2>大小</h2></th>
            <th><h2>上传时间</h2></th>
        </tr>
        <c:forEach begin="0" end="${sessionScope.tpDocList.size()-1}" var="i" step="1">
            <tr>
                <td><h3><a href="#">${sessionScope.tpDocList.get(i).dname}</a></h3></td>
                <td><h3><a href="#">${sessionScope.tpDocList.get(i).dsize}KB</a></h3></td>
                <td><h3><a href="#">${sessionScope.tpDocList.get(i).dupload}</a></h3></td>
            </tr>
        </c:forEach>
    </table>
</div>
<div id="fileForm">
    <div id="upload">
        <br/>
        <h2>文件上传</h2>
        <form class="form-control" action="fileUpload?fid=${sessionScope.fid}" method="post" enctype="multipart/form-data">
            选择文件：<input type="file" name="upload"/><br/>
            <input type="submit" onclick="func" value="上传" class="button button-3d button-primary button-rounded"/>
        </form>
    </div>
    <div id="folder">
        <br/>
        <h2>新建文件夹</h2>
        <form class="form-control" action="createFile" method="POST" accept-charset="utf-8" >
            文件夹名：<input type="text" name="folderName"/><br/>
            <input type="hidden" name="path" value="" />
            <input type="submit" value="新建" class="button button-3d button-primary button-rounded"/>
        </form>
    </div>
</div>
</body>
</html>

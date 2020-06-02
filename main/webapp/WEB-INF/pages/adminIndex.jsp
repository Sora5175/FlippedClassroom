<%--
  Created by IntelliJ IDEA.
  User: 28678
  Date: 2020/4/14
  Time: 12:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户首页</title>
    <link rel="stylesheet" href="../css/index.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="../css/jquery.sPage.css">
    <link rel="stylesheet" href="../css/animate.min.css" />
    <link rel="stylesheet" href="../css/syalert.min.css" />
    <script src="https://pv.sohu.com/cityjson"></script>
    <script type="text/javascript" src="../js/jquery-3.3.1.js"></script>
    <script src="../js/syalert.min.js"></script>
    <script src="../js/jquery.sPage.min.js"></script>
    <script type="text/javascript" src="../js/jquery.cookie.js"></script>
    <script type="text/javascript" src="../js/qrcode.js"></script>
    <script type="text/javascript" src="../js/adminIndex.js"></script>
</head>
<body>
<div id="header">
    <label id="user_name">管理员</label>
</div>
<div id="body">
    <div class="main_box admin_index_box">
        <div class="main_box_header">
            <h2 class="main_box_titile">审批申请</h2>
        </div>
        <div class="main_box_body">
            <div class="main_box_content">
                <div class="box_content_header">
                    <div class="select">
                        <select class="order">
                            <option value="1">时间升序</option>
                            <option value="2" selected>时间降序</option>
                            <option value="3">学号升序</option>
                            <option value="4">学号降序</option>
                            <option value="5">学校升序</option>
                            <option value="6">学校降序</option>
                            <option value="7">姓名升序</option>
                            <option value="8">姓名降序</option>
                        </select>
                    </div>
                    <div class="search bar6">
                        <div>
                            <input type="text" placeholder="条件查询" name="cname" id="key_name">
                            <button id="check_apply"></button>
                        </div>
                    </div>
                </div>
                <table cellspacing="0" class="box_content_body">
                    <thead class="thead">
                    <tr>
                        <th class="table-cell">学校</th>
                        <th class="table-cell">学号/工号</th>
                        <th class="table-cell">姓名</th>
                        <th class="table-cell">操作</th>
                    </tr>
                    </thead>
                    <tbody class="tbody">

                    </tbody>
                </table>
                <div class="page" id="apply_page"></div>
            </div>
        </div>
    </div>
</div>
<!-- 自定义弹窗 -->
<div class="sy-alert sy-alert-alert animated" sy-enter="zoomIn" sy-leave="zoomOut" sy-type="confirm" sy-mask="true" id="apply_lid">
    <div class="sy-title">申请详情</div>
    <div class="sy-content">
        <div class="form">

        </div>
    </div>
    <div class="sy-btn">
        <button class="cancel">关闭</button>
    </div>
</div>
</body>
</html>
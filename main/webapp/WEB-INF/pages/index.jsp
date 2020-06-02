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
    <script type="text/javascript" src="../js/index.js"></script>
</head>
<body>
    <div id="header">

    </div>
    <div id="body">
        <div class="main_box index_box">
            <div class="main_box_header">
                <h2 class="main_box_titile">我的课程</h2>
            </div>
            <div class="main_box_body">
                <div class="main_box_content">
                    <div class="box_content_header">
                        <div class="select">
                            <select class="order">
                                <option value="1">时间升序</option>
                                <option value="2" selected>时间降序</option>
                                <option value="3">名称升序</option>
                                <option value="4">名称降序</option>
                            </select>
                        </div>
                        <div class="search bar6">
                            <div>
                                <input type="text" placeholder="条件查询" name="cname" id="key_name">
                                <button id="check_course"></button>
                            </div>
                        </div>
                        <button class="action_button" id="add_course_action" onClick="syalert.syopen('add_course_lid')">添加课程</button>
                    </div>
                    <table cellspacing="0" class="box_content_body">
                        <thead class="thead">
                            <tr>
                                <th class="table-cell">课程名</th>
                                <th class="table-cell">操作</th>
                            </tr>
                        </thead>
                        <tbody class="tbody">

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!-- 自定义弹窗 -->
    <div class="sy-alert sy-alert-model animated" sy-enter="zoomIn" sy-leave="zoomOut" sy-type="confirm" sy-mask="true" id="add_course_lid">
        <div class="sy-title">添加课程</div>
        <div class="sy-content">
            <div class="form">
                <p class="input-item"><input type="text" placeholder="课程名" id="add_course_name"/></p>
            </div>
        </div>
        <div class="sy-btn">
            <button class="cancel">取消</button>
            <button class="confirm">确定</button>
        </div>
    </div>
</body>
</html>

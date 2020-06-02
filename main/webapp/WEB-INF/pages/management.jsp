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
    <link rel="stylesheet" href="../css/jquery.searchableSelect.css">
    <script src="https://pv.sohu.com/cityjson"></script>
    <script type="text/javascript" src="../js/jquery-3.3.1.js"></script>
    <script src="../js/syalert.min.js"></script>
    <script src="../js/jquery.sPage.min.js"></script>
    <script type="text/javascript" src="../js/jquery.cookie.js"></script>
    <script type="text/javascript" src="../js/qrcode.js"></script>
    <script type="text/javascript" src="../js/jquery.searchableSelect.js"></script>
    <script type="text/javascript" src="../js/management.js"></script>
</head>
<body>
<div id="header">

</div>
<div id="body">
    <div class="main_box management_box course_box">
        <div class="main_box_header">
            <h2 class="main_box_titile">课程管理</h2>
        </div>
        <div class="main_box_body">
            <div class="main_box_content">
                <div class="box_content_header">
                    <button class="action_button" id="add_blog_action">发布动态</button>
                    <button class="action_button" id="export_record_action">导出缺答记录</button>
                    <button class="action_button" id="delete_course_action">删除课程</button>
                </div>
            </div>
        </div>
    </div>

    <div class="main_box management_box question_box">
        <div class="main_box_header">
            <h2 class="main_box_titile">题库管理</h2>
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
                            <button id="check_question"></button>
                        </div>
                    </div>
                    <button class="action_button" id="import_question_action"  onClick="syalert.syopen('import_question_lid')">导入题库</button>
                </div>
                <table cellspacing="0" class="box_content_body">
                    <thead class="thead">
                    <tr>
                        <th class="table-cell">题目</th>
                        <th class="table-cell">操作</th>
                    </tr>
                    </thead>
                    <tbody class="tbody">
                    <tr>

                    </tr>
                    </tbody>
                </table>
                <div class="page" id="page"></div>
            </div>
        </div>
    </div>
    <div class="main_box management_box key_box">
        <div class="main_box_header">
            <h2 class="main_box_titile">关键词管理</h2>
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
                            <input type="text" placeholder="条件查询" name="cname" id="keyword_name">
                            <button id="check_keyword"></button>
                        </div>
                    </div>
                    <button class="action_button" id="add_keyword_action"  onClick="syalert.syopen('add_keyword_lid')">添加关键词</button>
                </div>
                <table cellspacing="0" class="box_content_body">
                    <thead class="thead">
                    <tr>
                        <th class="table-cell">关键词</th>
                        <th class="table-cell">操作</th>
                    </tr>
                    </thead>
                    <tbody class="tbody">
                    <tr>

                    </tr>
                    </tbody>
                </table>
                <div class="page" id="keyword_page"></div>
            </div>
        </div>
    </div>
</div>
<!-- 确认弹窗 -->
<div class="sy-alert animated" sy-enter="zoomIn" sy-leave="zoomOut" sy-type="confirm" sy-mask="true" id="delete_question_lid">
    <div class="sy-title">提示</div>
    <div class="sy-content">删除此问题？</div>
    <div class="sy-btn">
        <button class="cancel">取消</button>
        <button class="confirm">确定</button>
    </div>
</div>
<!-- 确认弹窗 -->
<div class="sy-alert sy-alert-alert animated" sy-enter="zoomIn" sy-leave="zoomOut" sy-type="alert" sy-mask="true" id="export_record_lid">
    <div class="sy-title">提示</div>
    <div class="sy-content">
        <div class="form">
            <p class="input-item"><button id="export_record_not_exported">导出新纪录</button></p>
            <p class="input-item"><button id="export_record_all">导出所有记录</button></p>
        </div>
    </div>
    <div class="sy-btn">
        <button class="cancel">关闭</button>
    </div>
</div>
<!-- 确认弹窗 -->
<div class="sy-alert sy-alert-alert animated" sy-enter="zoomIn" sy-leave="zoomOut" sy-type="alert" sy-mask="true" id="delete_course_lid">
    <div class="sy-title">提示</div>
    <div class="sy-content">
        <div class="form">
            <div id="delete_course_qrcode"></div>
            <p class="input-item">微信小程序扫描确认操作</p>
        </div>
    </div>
    <div class="sy-btn">
        <button class="cancel">关闭</button>
    </div>
</div>
<!-- 自定义弹窗 -->
<div class="sy-alert sy-alert-model animated" sy-enter="zoomIn" sy-leave="zoomOut" sy-type="confirm" sy-mask="true" id="import_question_lid">
    <div class="sy-title">导入题库</div>
    <div class="sy-content">
        <div class="form">
            <p class="input-item">请选择题库文件（Excel文件: .xls或.xlsx）</p>
            <p class="input-item"><input type="file" value="选择文件" id="import_question_file"/></p>
            <p class="input-item"><a href="/sources/importQuestion.xlsx">查看示范</a></p>
        </div>
    </div>
    <div class="sy-btn">
        <button class="cancel">取消</button>
        <button class="confirm">确定</button>
    </div>
</div>
<div class="sy-alert sy-alert-model animated" sy-enter="zoomIn" sy-leave="zoomOut" sy-type="confirm" sy-mask="true" id="add_keyword_lid">
    <div class="sy-title">添加关键词</div>
    <div class="sy-content">
        <div class="form">
            <p class="input-item"><input type="text" placeholder="关键词名称" id="add_keyword_name"/></p>
        </div>
    </div>
    <div class="sy-btn">
        <button class="cancel">取消</button>
        <button class="confirm">确定</button>
    </div>
</div>
<div class="sy-alert animated" sy-enter="zoomIn" sy-leave="zoomOut" sy-type="confirm" sy-mask="true" id="delete_keyword_lid">
    <div class="sy-title">提示</div>
    <div class="sy-content">删除此关键词？</div>
    <div class="sy-btn">
        <button class="cancel">取消</button>
        <button class="confirm">确定</button>
    </div>
</div>
<!-- 自定义弹窗 -->
<div class="sy-alert sy-alert-model animated" sy-enter="zoomIn" sy-leave="zoomOut" sy-type="confirm" sy-mask="true" id="add_blog_lid">
    <div class="sy-title">发布动态</div>
    <div class="sy-content">
        <div class="form">
            <p class="input-item"><textarea data-v-3552b974="" maxlength="2000" class="text-area-box-v2-val" placeholder="添加描述" id="add_blog_description"></textarea></p>
            <p class="input-item">请选择视频文件(.mp4)</p>
            <p class="input-item"><input type="file" value="选择文件" id="add_blog_file"/></p>
            <p class="input-item">选择关键词</p>
            <p class="input-item select_p"><select id="add_blog_keywords"></select><button id="add_blog_keyword_confirm">添加</button></p>
            <div class="input-item" id="selected_keywords"></div>
        </div>
    </div>
    <div class="sy-btn">
        <button class="cancel">取消</button>
        <button class="confirm">确定</button>
    </div>
</div>
<div id="menu">
    <p id="menu_delete">删除</p>
</div>
</body>
</html>

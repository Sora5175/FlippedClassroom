var token = $.cookie('token');
var userInfo = null;
var course = JSON.parse($.cookie('course'));
var questionList = [];
var question = null;
var showQuestionList = [];
var showKeyWordList = [];
var keyword = null;
var ws;
var selectedKeyword = [];
var deleteSelectedKeywordIndex = -1;
var keywordListStorage = [];
$(function () {
    document.oncontextmenu = function(e){
        return false;
    }
    getQuestionList();
    getKeywordList();
    $.ajax({
        url:"https://www.njitcommunity.top/FlippedClassroom/user/getMyInfo",
        headers: {
            'content-type': 'application/json',
            'token': token
        },
        success:function(res){
            userInfo = res.result;
            $("#header").html('        <img id="user_img" src="'+userInfo.avatarUrl+'"/>\n' +
                '        <label id="user_name">'+userInfo.workId+'   '+userInfo.name+'</label>');
        }
    });
    $("body").on("mousedown",".sy-alert",function(e) {
        if(e.which == 1){  //如果鼠标左键点下，则隐藏右键菜单
            $("#menu").hide();
        }
    });
    $("body").on("mousedown",".sy-mask",function(e) {
        if(e.which == 1){  //如果鼠标左键点下，则隐藏右键菜单
            $("#menu").hide();
        }
    });
    $(".course_box").on('click','#add_blog_action',function(e){
        initKeywordSelect();
        syalert.syopen("add_blog_lid");
    });
    $(".course_box").on('click','#export_record_action',function(e){
        syalert.syopen("export_record_lid");
    });
    $("#export_record_lid").on('click','#export_record_all',function (e) {
        findAllByCourseIdWithExcel();
    });
    $("#export_record_lid").on('click','#export_record_not_exported',function (e) {
        findAllNotExportedByCourseIdWithExcel();
    });
    $("#export_record_lid").on('click','.cancel',function (e) {
        syalert.syhide("export_record_lid");
    });
    $("#add_blog_lid").on("click","#add_blog_keyword_confirm",function (e) {
        selectKeyword();
    });

    $('#selected_keywords').on("mousedown",".selected_keyword",function(e) {
        deleteSelectedKeywordIndex = $(this).index();
        if (e.which == 3) {  // 1 = 鼠标左键; 2 = 鼠标中键; 3 = 鼠标右键
            var x = e.originalEvent.x || e.originalEvent.layerX || 0;
            var y = e.originalEvent.y || e.originalEvent.layerY || 0;
            $("#menu").css({"left":x+"px","top":y+"px"});
            $("#menu").show();
        }
    });
    $("#menu").on("click","#menu_delete",function (e) {
        deleteSelectedKeyword();
    });
    $(".question_box .tbody").on('click','a',function(e){
        var index = $(this).parents("tr").index();
        question = showQuestionList[index];
    });
    $(".key_box .tbody").on('click','a',function(e){
        var index = $(this).parents("tr").index();
        keyword = showKeyWordList[index];
    });
    $(".question_box").on('click','#check_question',function(e){
        getQuestionListWithKey();
    });
    $(".key_box").on('click','#check_keyword',function(e){
        getKeywordListWithKey();
    });


    //删除课程
    $("#delete_course_lid").on("click",".cancel",function () {
        syalert.syhide("delete_course_lid");
        ws.close();
        $("#delete_course_qrcode").html("");
    });
    //删除问题
    $("#delete_question_lid").on("click",".cancel",function () {
        question = null;
        syalert.syhide("delete_question_lid");
    });
    $("#delete_question_lid").on("click",".confirm",function () {
        deleteQuestionAction();
    });
    //导入问题
    $("#import_question_lid").on("click",".cancel",function () {
        $("#import_question_file").val("");
        syalert.syhide("import_question_lid");
    });
    $("#import_question_lid").on("click",".confirm",function () {
        importQuestions();
    });
    //添加关键词
    $("#add_keyword_lid").on("click",".cancel",function () {
        syalert.syhide("add_keyword_lid");
    });
    $("#add_keyword_lid").on("click",".confirm",function () {
        addKeywordAction();
    });
    //删除关键词
    $("#delete_keyword_lid").on("click",".cancel",function () {
        syalert.syhide("delete_keyword_lid");
    });
    $("#delete_keyword_lid").on("click",".confirm",function () {
        deleteKeywordAction();
    });

    //发布动态
    $("#add_blog_lid").on("click",".cancel",function () {
        $("#add_blog_file").val("");
        $("#add_blog_description").val("");
        $(".select_p").html('<select id="add_blog_keywords"></select><button id="add_blog_keyword_confirm">添加</button>');
        syalert.syhide("add_blog_lid");
    });
    $("#add_blog_lid").on("click",".confirm",function () {
        addBlogAction();
        syalert.syhide("add_blog_lid");
    });

    //修改排序
    $(".question_box").on("change","select",function () {
        questionOrder();
    });
    $(".key_box").on("change","select",function () {
        keywordOrder();
    });

    $(".management_box").on("click","#delete_course_action",function () {
        var ip = returnCitySN["cip"];
        var courseTemp = new Object();
        courseTemp.ip = ip;
        courseTemp.courseId = course.id;
        var qrcode = new QRCode(document.getElementById("delete_course_qrcode"), {
            width : 100,
            height : 100
        });
        qrcode.makeCode(JSON.stringify(courseTemp));
        ws = new WebSocket("wss://www.njitcommunity.top/FlippedClassroom/webDeleteCourse?ip="+ip,[token]);
        ws.onmessage = function (event) {
            if(token == event.data){
                $.ajax({
                    url:"https://www.njitcommunity.top/FlippedClassroom/course/deleteCourse",
                    headers: {
                        'content-type': 'application/json',
                        'token': token
                    },
                    data:{
                        courseJson : JSON.stringify(course)
                    },
                    success:function(res){
                        syalert.syhide("delete_course_lid");
                        window.location.href="index";
                    },
                    fail:function () {
                        alert("服务器繁忙，请稍后重试");
                    }
                });
            }
            ws.close();
        }
        syalert.syopen('delete_course_lid');
    });
})
getQuestionList = function(){
    $.ajax({
        url:"https://www.njitcommunity.top/FlippedClassroom/question/findAllByCourseId",
        headers: {
            'content-type': 'application/json',
            'token': token
        },
        data:{
            courseId: course.id
        },
        success:function(res){
            questionList = res.result;
            questionOrder();
        }
    });
};
getQuestionListWithKey = function(){
    var key = $("#key_name").val();
    if(key == ""){
        getQuestionList();
    }else{
        $.ajax({
            url:"https://www.njitcommunity.top/FlippedClassroom/question/findAllByCourseIdAndKey",
            headers: {
                'content-type': 'application/json',
                'token': token
            },
            data:{
                courseId: course.id,
                key:key
            },
            success:function(res){
                questionList = res.result;
                $("#key_name").val("");
                questionOrder();
            }
        });
    }
};
getKeywordList = function () {
    $.ajax({
        url:"https://www.njitcommunity.top/FlippedClassroom/keyword/findAllByCourseId",
        headers: {
            'content-type': 'application/json',
            'token': token
        },
        data:{
            courseId: course.id
        },
        success:function(res){
            course.keywordList = res.result;
            keywordListStorage = course.keywordList;
            keywordOrder();
        }
    });
};
getKeywordListWithKey = function(){
    var key = $("#keyword_name").val().toLowerCase();
    if(key == ""){
        getKeywordList();
    }else{
        var keywordListTemp = [];
        for(var i = 0;i<keywordListStorage.length;i++){
            if(keywordListStorage[i].name.toLowerCase().indexOf(key)>-1){
                keywordListTemp.push(keywordListStorage[i]);
            }
        }
        course.keywordList = keywordListTemp;
        $("#keyword_name").val("");
        keywordOrder();
    }
};
setPage = function(pageSize){
    if(questionList.length<10){
        showQuestionList = questionList;
    }else{
        showQuestionList = [];
        for(var i=0;i<pageSize;i++){
            showQuestionList.push(questionList[i]);
        }
    }
    setShowQuestionList();
    $("#page").sPage({
        page:1,
        total:questionList.length,//数据总条数，必填
        pageSize:pageSize,//每页显示多少条数据，默认10条
        showTotal:true,//是否显示总条数，默认关闭：false
        totalTxt:"共{total}条",//数据总条数文字描述，{total}为占位符，默认"共{total}条"
        noData: false,//没有数据时是否显示分页，默认false不显示，true显示第一页-->
        showSkip:true,//是否显示跳页，默认关闭：false
        showPN:true,//是否显示上下翻页，默认开启：true
        prevPage:"上一页",//上翻页文字描述，默认“上一页”
        nextPage:"下一页",//下翻页文字描述，默认“下一页”
        fastForward: 0,//快进快退页数，默认0表示不开启快进快退
        backFun:function(page){
            if(page*pageSize > questionList.length){
                showQuestionList = [];
                for(var i=(page-1)*pageSize;i<questionList.length;i++){
                    showQuestionList.push(questionList[i]);
                }
            }else{
                showQuestionList = [];
                for(var i=(page-1)*pageSize;i<page*pageSize;i++){
                    showQuestionList.push(questionList[i]);
                }
            }
            setShowQuestionList();
        }
    });
};
setKeywordPage = function(pageSize){
    if(course.keywordList.length<10){
        showKeyWordList = course.keywordList;
    }else{
        showKeyWordList = [];
        for(var i=0;i<pageSize;i++){
            showKeyWordList.push(course.keywordList[i]);
        }
    }
    setShowKeywordList();
    $("#keyword_page").sPage({
        page: 1,
        total: course.keywordList.length,//数据总条数，必填
        pageSize: pageSize,//每页显示多少条数据，默认10条
        showTotal: true,//是否显示总条数，默认关闭：false
        totalTxt: "共{total}条",//数据总条数文字描述，{total}为占位符，默认"共{total}条"
        noData: false,//没有数据时是否显示分页，默认false不显示，true显示第一页-->
        showSkip: true,//是否显示跳页，默认关闭：false
        showPN: true,//是否显示上下翻页，默认开启：true
        prevPage: "上一页",//上翻页文字描述，默认“上一页”
        nextPage: "下一页",//下翻页文字描述，默认“下一页”
        fastForward: 0,//快进快退页数，默认0表示不开启快进快退
        backFun: function (page) {
            if (page*pageSize > course.keywordList.length) {
                showKeyWordList = [];
                for (var i = (page - 1) * pageSize; i < course.keywordList.length; i++) {
                    showKeyWordList.push(course.keywordList[i]);
                }
            } else {
                showKeyWordList = [];
                for (var i = (page - 1) * pageSize; i < page * pageSize; i++) {
                    showKeyWordList.push(course.keywordList[i]);
                }
            }
            setShowKeywordList();
        }
    });
}
setShowQuestionList = function(){
    var html = "";
    for(var i = 0;i<showQuestionList.length;i++){
        html += '<tr>\n' +
            '                                <td class="table-cell">'+showQuestionList[i].content+'</td>\n' +
            '                                <td class="table-cell"><a href="javascript:;"  onClick="syalert.syopen(\'delete_question_lid\')">删除</a></td>\n' +
            '                            </tr>';
    }
    $(".question_box .tbody").html(html);
};
setShowKeywordList = function(){
    var html = "";
    for(var i = 0;i<showKeyWordList.length;i++){
        html += '<tr>\n' +
            '                                <td class="table-cell">'+showKeyWordList[i].name+'</td>\n' +
            '                                <td class="table-cell"><a href="javascript:;"  onClick="syalert.syopen(\'delete_keyword_lid\')">删除</a></td>\n' +
            '                            </tr>';
    }
    $(".key_box .tbody").html(html);
};
deleteQuestionAction = function () {
    $.ajax({
        url:"https://www.njitcommunity.top/FlippedClassroom/question/deleteQuestion",
        headers: {
            'content-type': 'application/json',
            'token': token
        },
        data:{
            questionJson:JSON.stringify(question)
        },
        success:function(res){
            question = null;
            syalert.syhide("delete_question_lid");
            getQuestionList();
        },
        fail:function () {
            alert("服务器繁忙，请稍后重试");
        }
    });
};
importQuestions = function () {
    var formData = new FormData();
    formData.append("questions",$('#import_question_file')[0].files[0]);
    formData.append("courseId", course.id);
    $.ajax({
        url:"https://www.njitcommunity.top/FlippedClassroom/question/importQuestions",
        headers: {
            'token': token
        },
        type: 'POST',
        data: formData,
        processData : false, // 使数据不做处理
        contentType : false, // 不要设置Content-Type请求头
        success:function(res){
            $("#import_question_file").val("");
            syalert.syhide("import_question_lid");
            getQuestionList();
        },
        fail:function () {
            alert("服务器繁忙，请稍后重试");
        }
    });
};
addKeywordAction = function () {
    var name = $("#add_keyword_name").val();
    $.ajax({
        url:"https://www.njitcommunity.top/FlippedClassroom/keyword/addKeyword",
        headers: {
            'content-type': 'application/json',
            'token': token
        },
        data:{
            name: name,
            courseId: course.id
        },
        success:function(res){
            $("#add_keyword_name").val("");
            syalert.syhide("add_keyword_lid");
            getKeywordList();
        },
        fail:function () {
            alert("服务器繁忙，请稍后重试");
        }
    });
};
deleteKeywordAction = function () {
    $.ajax({
        url:"https://www.njitcommunity.top/FlippedClassroom/keyword/deleteKeyword",
        headers: {
            'content-type': 'application/json',
            'token': token
        },
        data:{
            keywordJson:JSON.stringify(keyword)
        },
        success:function(res){
            keyword = null
            syalert.syhide("delete_keyword_lid");
            getKeywordList();
        },
        fail:function () {
            alert("服务器繁忙，请稍后重试");
        }
    });
};
questionOrder = function(){
    var index = $(".question_box select").val();
    if(index == 1){
        questionTimeOrder(true);
    }else if(index == 2){
        questionTimeOrder(false);
    }else if(index == 3){
        questionNameOrder(true);
    }else if(index == 4){
        questionNameOrder(false);
    }
};
keywordOrder = function(){
    var index = $(".key_box select").val();
    if(index == 1){
        keywordTimeOrder(true);
    }else if(index == 2){
        keywordTimeOrder(false);
    }else if(index == 3){
        keywordNameOrder(true);
    }else if(index == 4){
        keywordNameOrder(false);
    }
};
questionTimeOrder = function (asc) {
    if(asc){
        questionList.sort(function (a,b) {
            return a.id - b.id;
        })
    }else{
        questionList.sort(function (a,b) {
            return b.id - a.id;
        })
    }
    setPage(10);
};
keywordTimeOrder = function (asc) {
    if(asc){
        course.keywordList.sort(function (a,b) {
            return a.id - b.id;
        })
    }else{
        course.keywordList.sort(function (a,b) {
            return b.id - a.id;
        })
    }
    setKeywordPage(10);
};
questionNameOrder = function(asc){
    if(asc){
        questionList.sort(function (a,b) {
            return a.content.localeCompare(b.content);
        })
    }else{
        questionList.sort(function (a,b) {
            return b.content.localeCompare(a.content);
        })
    }
    setPage(10);
}
keywordNameOrder = function (asc) {
    if(asc){
        course.keywordList.sort(function (a,b) {
            return a.name.localeCompare(b.name);
        })
    }else{
        course.keywordList.sort(function (a,b) {
            return b.name.localeCompare(a.name);
        })
    }
    setKeywordPage(10);
};
initKeywordSelect = function () {
    var html = "";
    for(var i=0;i<course.keywordList.length;i++){
        html += '<option value="'+i+'">'+course.keywordList[i].name+'</option>'
    }
    $('#add_blog_keywords').html(html);
    $('#add_blog_keywords').searchableSelect();
    selectedKeyword = [];
    setSelectedKeyword();
};
selectKeyword = function () {
    var index = parseInt($(".searchable-select-items .selected").attr("data-value"));
    var keywordTemp = course.keywordList[index];
    for(var i=0;i<selectedKeyword.length;i++){
        if(keywordTemp.id == selectedKeyword[i].id){
            return;
        }
    }
    console.log(selectedKeyword);
    selectedKeyword.push(keywordTemp);
    setSelectedKeyword();
};
setSelectedKeyword = function () {
    var html = "";
    for(var i=0;i<selectedKeyword.length;i++){
        html += '<div class="selected_keyword">'+selectedKeyword[i].name+'</div>';
    }
    $("#selected_keywords").html(html);
};
deleteSelectedKeyword = function () {
    if(deleteSelectedKeywordIndex!=-1){
        selectedKeyword.splice(deleteSelectedKeywordIndex,1);
        deleteSelectedKeywordIndex = -1;
        $("#menu").hide();
        setSelectedKeyword();
    }
};
addBlogAction = function () {
    var blog = new Object();
    blog.description = $("#add_blog_description").val();
    blog.course = course;
    blog.keywordList = selectedKeyword;
    var blogJson = JSON.stringify(blog);
    var formData = new FormData();
    formData.append("source",$('#add_blog_file')[0].files[0]);
    formData.append("blogJson", blogJson);
    $.ajax({
        url:"https://www.njitcommunity.top/FlippedClassroom/blog/addBlog",
        headers: {
            'token': token
        },
        type: 'POST',
        data: formData,
        processData : false, // 使数据不做处理
        contentType : false, // 不要设置Content-Type请求头
        success:function(res){
            $("#add_blog_file").val("");
            $("#add_blog_description").val("");
            $(".select_p").html('<select id="add_blog_keywords"></select><button id="add_blog_keyword_confirm">添加</button>');
            syalert.syhide("add_blog_lid");
        },
        fail:function () {
            alert("服务器繁忙，请稍后重试");
        }
    });
};
findAllByCourseIdWithExcel = function () {
    download("https://www.njitcommunity.top/FlippedClassroom/absenceRecord/findAllByCourseIdWithExcel?courseId="+course.id);
};
findAllNotExportedByCourseIdWithExcel = function () {
    download("https://www.njitcommunity.top/FlippedClassroom/absenceRecord/findAllNotExportedByCourseIdWithExcel?courseId="+course.id)
};
var download = function(url){
    var xhr = new XMLHttpRequest();		//定义http请求对象
    xhr.open("GET", url, true);
    xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xhr.setRequestHeader("token",token);
    xhr.send();
    xhr.responseType = "blob";  // 返回类型blob
    xhr.onload = function() {   // 定义请求完成的处理函数，请求前也可以增加加载框/禁用下载按钮逻辑
        if (this.status===200) {
            var blob = this.response;
            let temp = xhr.getResponseHeader("Content-disposition").split(";")[1].split("filename=")[1];
            var fileName = decodeURIComponent(temp);
            var a = document.createElement('a');
            let objectUrl = URL.createObjectURL(blob);
            a.setAttribute("href", objectUrl);
            a.setAttribute("download", fileName);
            a.click();
            syalert.syhide("export_record_lid");
        }
        else{
            alert("出现了未知的错误!");
        }
    }
};
var token = $.cookie('token');
var userInfo = null;
var courseList = [];
var course = null;
var courseListStorage = [];
$(function () {
    $.ajax({
        url:"https://www.njitcommunity.top/FlippedClassroom/user/getMyInfo",
        headers: {
            'content-type': 'application/json',
            'token': token
        },
        success:function(res){
            userInfo = res.result;
            if(userInfo.role == 1){
                window.location.href = "adminIndex";
            }
            $("#header").html('        <img id="user_img" src="'+userInfo.avatarUrl+'"/>\n' +
                '        <label id="user_name">'+userInfo.workId+'   '+userInfo.name+'</label>');
        }
    });
    getMyCourses();

    $(".index_box").on('click','#check_course',function (e) {
        getCourseListWithKey();
    });

    $(".index_box .tbody").on('click','a',function(e){
        var index = $(this).parents("tr").index();
        course = courseList[index];
        $.cookie('course', JSON.stringify(course));
        window.location.href = "management";
    });
    //添加课程
    $("#add_course_lid").on("click",".cancel",function (e) {
        $("#add_course_name").val("");
        syalert.syhide('add_course_lid');
    });
    $("#add_course_lid").on("click",".confirm",function (e) {
        var courseName = $("#add_course_name").val();
        addCourseAction(courseName);
    });

    //修改排序
    $(".index_box").on("change","select",function () {
        courseOrder();
    });
})
getMyCourses = function(){
    $.ajax({
        url:"https://www.njitcommunity.top/FlippedClassroom/course/findMyCourses",
        headers: {
            'content-type': 'application/json',
            'token': token
        },
        success:function(res){
            courseList = res.result;
            courseListStorage = courseList;
            courseOrder();
        }
    });
};
setCourseList = function() {
    var html = "";
    for(var i = 0;i<courseList.length;i++){
        html += '<tr>\n' +
            '                                <td class="table-cell">'+courseList[i].name+'</td>\n' +
            '                                <td class="table-cell"><a href="javascript:;">管理</a></td>\n' +
            '                            </tr>';
    }
    $(".index_box .tbody").html(html);
};
addCourseAction = function (courseName) {
    $.ajax({
        url:"https://www.njitcommunity.top/FlippedClassroom/course/addCourse",
        headers: {
            'content-type': 'application/json',
            'token': token
        },
        data:{
            name:courseName
        },
        success:function(res){
            $("#add_course_name").val("");
            syalert.syhide('add_course_lid');
            getMyCourses();
        },
        fail:function () {
            alert("服务器繁忙，请稍后重试");
        }
    });
};
getCourseListWithKey = function () {
    var key = $("#key_name").val().toLowerCase();
    if(key == ""){
        getMyCourses();
    }else{
        var courseListTemp = [];
        for(var i = 0;i<courseListStorage.length;i++){
            if(courseListStorage[i].name.toLowerCase().indexOf(key)>-1){
                courseListTemp.push(courseListStorage[i]);
            }
        }
        courseList = courseListTemp;
        $("#key_name").val("");
        courseOrder();
    }
};
courseOrder = function () {
    var index = $(".index_box select").val();
    if(index == 1){
        courseTimeOrder(true);
    }else if(index == 2){
        courseTimeOrder(false);
    }else if(index == 3){
        courseNameOrder(true);
    }else if(index == 4){
        courseNameOrder(false);
    }
};
courseTimeOrder = function (asc) {
    if(asc){
        courseList.sort(function (a,b) {
            return a.id - b.id;
        })
    }else{
        courseList.sort(function (a,b) {
            return b.id - a.id;
        })
    }
    setCourseList();
};
courseNameOrder = function (asc) {
    if(asc){
        courseList.sort(function (a,b) {
            return a.name.localeCompare(b.name);
        })
    }else{
        courseList.sort(function (a,b) {
            return b.name.localeCompare(a.name);
        })
    }
    setCourseList();
};
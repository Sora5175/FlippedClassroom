var token = $.cookie('token');
var userInfo = null;
var applyList = [];
var showApplyList = [];
var applyListStorage = [];
var apply = null;
$(function () {
    $.ajax({
        url: "https://www.njitcommunity.top/FlippedClassroom/user/getMyInfo",
        headers: {
            'content-type': 'application/json',
            'token': token
        },
        success: function (res) {
            userInfo = res.result;
        }
    });
    getApplyList();
    $(".admin_index_box .tbody").on('click','a',function(e){
        var index = $(this).parents("tr").index();
        apply = showApplyList[index];
        setApplyLid();
        syalert.syopen("apply_lid");
    });
    $("#apply_lid").on("click",".cancel",function () {
        apply = null;
        syalert.syhide("apply_lid");
    });
    $("#apply_lid").on("click","#apply_agree",function () {
        agreeeApply();
    });
    $("#apply_lid").on("click","#apply_disagree",function () {
        disAgreeApply();
    });
    $(".admin_index_box").on('click','#check_apply',function(e){
        getApplyListWithKey();
    });
});
setApplyLid = function(){
  var html = '<p class="input-item">学校：<span>'+apply.school.name+'</span></p>\n' +
      '            <p class="input-item">学号/工号：<span>'+apply.student.workId+'</span></p>\n' +
      '            <p class="input-item">姓名：<span>'+apply.student.name+'</span></p>\n' +
      '            <p class="input-item">性别：<span>'+apply.student.sex+'</span></p>\n' +
      '            <p class="input-item"><input type="button" value="同意" id="apply_agree"/>\n' +
      '                <input type="button" value="拒绝" id="apply_disagree"/>\n' +
      '            </p>';
  $("#apply_lid .form").html(html);
};
getApplyList = function () {
    $.ajax({
        url: "https://www.njitcommunity.top/FlippedClassroom/apply/findAll",
        headers: {
            'content-type': 'application/json',
            'token': token
        },
        success: function (res) {
            applyList = res.result;
            applyListStorage = applyList;
            applyOrder();
        }
    });
};
setShowApplyList = function(){
    var html = "";
    for(var i = 0;i<showApplyList.length;i++){
        html += '<tr> <td class="table-cell">'+showApplyList[i].school.name+'</td> <td class="table-cell">'+showApplyList[i].student.workId+'</td> <td class="table-cell">'+showApplyList[i].student.name+'</td> <td class="table-cell"><a href="javascript:;">管理</a></td> </tr>';
    }
    $(".admin_index_box .tbody").html(html);
};
applyOrder = function () {
    var index = $(".admin_index_box select").val();
    if(index == 1){
        applyTimeOrder(true);
    }else if(index == 2){
        applyTimeOrder(false);
    }else if(index == 3){
        applyIdOrder(true);
    }else if(index == 4){
        applyIdOrder(false);
    }else if(index == 5){
        applySchoolOrder(false);
    }else if(index == 6){
        applySchoolOrder(true);
    }else if(index == 7){
        applyNameOrder(false);
    }else if(index == 8){
        applyNameOrder(false);
    }
};
applyTimeOrder = function (asc) {
    if(asc){
        applyList.sort(function (a,b) {
            return a.id - b.id;
        })
    }else{
        applyList.sort(function (a,b) {
            return b.id - a.id;
        })
    }
    setApplyPage(10);
};
applyIdOrder = function (asc) {
    if(asc){
        applyList.sort(function (a,b) {
            return a.student.workId.localeCompare(b.student.workId);
        })
    }else{
        applyList.sort(function (a,b) {
            return b.student.workId.localeCompare(a.student.workId);
        })
    }
    setApplyPage(10);
};
applySchoolOrder = function(asc){
    if(asc){
        applyList.sort(function (a,b) {
            return a.school.name.localeCompare(b.school.name);
        })
    }else{
        applyList.sort(function (a,b) {
            return b.school.name.localeCompare(a.school.name);
        })
    }
    setApplyPage(10);
};
applyNameOrder = function (asc) {
    if(asc){
        courseList.sort(function (a,b) {
            return a.student.name.localeCompare(b.student.name);
        })
    }else{
        courseList.sort(function (a,b) {
            return b.student.name.localeCompare(a.student.name);
        })
    }
    setApplyPage(10);
};
setApplyPage = function (pageSize) {
    if(applyList.length<10){
        showApplyList = applyList;
    }else{
        showApplyList = [];
        for(var i=0;i<pageSize;i++){
            showApplyList.push(applyList[i]);
        }
    }
    setShowApplyList();
    $("#apply_page").sPage({
        page: 1,
        total: applyList.length,//数据总条数，必填
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
            if (page*pageSize > applyList.length) {
                showApplyList = [];
                for (var i = (page - 1) * pageSize; i < applyList.length; i++) {
                    showApplyList.push(applyList[i]);
                }
            } else {
                showApplyList = [];
                for (var i = (page - 1) * pageSize; i < page * pageSize; i++) {
                    showApplyList.push(applyList[i]);
                }
            }
            setShowApplyList();
        }
    });
};
getApplyListWithKey = function(){
    var key = $("#key_name").val().toLowerCase();
    if(key == ""){
        getApplyList();
    }else{
        var applyListTemp = [];
        for(var i = 0;i<applyListStorage.length;i++){
            if(applyListStorage[i].school.name.toLowerCase().indexOf(key)>-1
                ||applyListStorage[i].student.name.toLowerCase().indexOf(key)>-1
                ||applyListStorage[i].student.workId.toLowerCase().indexOf(key)>-1){
                applyListTemp.push(applyListStorage[i]);
            }
        }
        applyList = applyListTemp;
        $("#keyword_name").val("");
        applyOrder();
    }
};
agreeeApply = function () {
    $.ajax({
        url: "https://www.njitcommunity.top/FlippedClassroom/apply/upgrade",
        headers: {
            'content-type': 'application/json',
            'token': token
        },
        data:{
          applyJson: JSON.stringify(apply)
        },
        success: function (res) {
            getApplyList();
            apply = null;
            syalert.syhide("apply_lid");
        }
    });
};
disAgreeApply = function () {
    $.ajax({
        url: "https://www.njitcommunity.top/FlippedClassroom/apply/deleteApply",
        headers: {
            'content-type': 'application/json',
            'token': token
        },
        data:{
            applyJson: JSON.stringify(apply)
        },
        success: function (res) {
            getApplyList();
            apply = null;
            syalert.syhide("apply_lid");
        }
    });
};

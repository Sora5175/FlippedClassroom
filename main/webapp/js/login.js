var token = $.cookie('token');;
$(function () {
    var ip = returnCitySN["cip"];
    var ipJson = new Object();
    ipJson.ip = ip;
    var qrcode = new QRCode(document.getElementById("login_qrcode"), {
        width : 100,
        height : 100
    });
    qrcode.makeCode(JSON.stringify(ipJson));
    if(token==null){
        var ws = new WebSocket("wss://www.njitcommunity.top/FlippedClassroom/webLogin?ip="+ip);
        ws.onmessage = function (event) {
            token = event.data;
            $.cookie('token', token);
            ws.close();
            window.location.href = "index";
        }
    }else{
        window.location.href = "index";
    }
})
//与后端链接的websocket地址
var websocketurl = "ws://" + window.location.hostname + ":8091/ws";
var reconnectflag = false;//避免重复连接
var socket;

function createWebSocket(url, callbak) {
    try {
        if (!window.WebSocket) {
            window.WebSocket = window.MozWebSocket;
        }
        if (window.WebSocket) {
            socket = new WebSocket(url);
            socket.binaryType = "arraybuffer";
            callbak();
        } else {
      	    alert("您当前的浏览器不支持websocket!!!");
        }
    } catch (e) {
        reconnect(url, callbak);
    }
}


function reconnect(url, callbak) {
    if (reconnectflag) return;
    reconnectflag = true;
    //没连接上会一直重连，设置延迟避免请求过多
    setTimeout(function () {
        createWebSocket(url, callbak);
        reconnectflag = false;
    }, 2000);
}

 
 


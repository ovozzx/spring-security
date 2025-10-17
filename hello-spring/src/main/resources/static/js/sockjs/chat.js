$().ready(function() {
    
    var socket = new SockJS("/chat");
    
    socket.onopen = function() {
        // 소켓 엔드포인트에 정상적인 접근이 완료됨!
        alert("Connected!");
        
        socket.send("접속했습니다~~~~");
    };
    
});
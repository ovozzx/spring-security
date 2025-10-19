var socket = null;
function sendChat(message, target) {
    socket.send(JSON.stringify({
                    "message": message,
                    // [2025-10-20] Chatting Room 보관
                    "room": target,
                    "action": "ONE-TO-ONE-CHAT"
                }));
    // [2025-10-20] Chatting Room 보관
    // alert("메시지를 전달했습니다!");
}

$().ready(function() {
    // [2025-10-20] Chatting Room 보관
    // var chatPopup = null;
    var chatRooms = {};
    
    socket = new SockJS("/chat");
    
    socket.onopen = function() {
        // 소켓 엔드포인트에 정상적인 접근이 완료됨!
        // [2025-10-20] Chatting Room 보관
        var username = $("#login-user-name").text();
        // [2025-10-20] Chatting Room 보관
        var userEmail = $("#login-user-email").text();
        
        socket.send(JSON.stringify({
                        "message": username + "님이 입장했습니다.", 
                        "username": username,
                        "userEmail": userEmail,
                        "action": "JOIN"
                    }));
    };
    
    socket.onmessage = function(data) {
        var pushedData = data.data;
        var chatData = JSON.parse(pushedData);
        
        if (chatData.action === "INVITE") {
            if ( confirm( chatData.username + chatData.message ) ) {
                //alert("OK");
                socket.send(JSON.stringify({
                                "message": "",
                                "target": chatData.userEmail,
                                "action": "INVITE_OK"
                            }));
                
                // [2025-10-20] Chatting Room 보관
                // chatPopup = window.open("/html/chat.html");
            }
            else {
                //alert("NO");
                socket.send(JSON.stringify({
                                "message": "",
                                "target": chatData.userEmail,
                                "action": "INVITE_DENY"
                            }));
            }
        }
        else if (chatData.action === "INVITE_FAIL") {
            alert(chatData.message);
        }
        else if (chatData.action === "INVITE_OK") {
            // [2025-10-20] Chatting Room 보관
            var chatPopup = window.open("/html/chat.html?room=" + chatData.room);
            chatRooms[chatData.room] = chatPopup;
        }
        else if (chatData.action === "INVITE_DENY") {
            alert(chatData.username + " 님이 대화를 거절했습니다.");
        }
        else if (chatData.action === "ONE-TO-ONE-CHAT") {
            // [2025-10-20] Chatting Room 보관
            chatRooms[chatData.room].receiveChat(chatData);
        }
        else {
            
        }
        
        /*
        // [2025-10-20] Chatting Room 보관
        var history = $("<li></li>");
        var loginUserEmail = $(".chat").children(".chat-message-input")
                                       .children(".user-email")
                                       .val();
        
        console.log(loginUserEmail, chatData.userEmail, loginUserEmail === chatData.userEmail)
                                       
        if (loginUserEmail === chatData.userEmail) {
            history.addClass("my-chat");
        }
        
        var username = $("<div></div>");
        username.text(chatData.username);
        
        var message = $("<div></div>");
        message.text(chatData.message);
        
        history.append(username);
        history.append(message);
        
        $(".chat").children(".history").append(history);
        */
    }
    
    $(".chat").children(".chat-message-input")
              .children(".message")
              .on("keyup", function(event) {
                console.log(event.keyCode);
                
                if (event.keyCode === 13) {
                    var message = $(this).val();
                    var username = $(this).closest(".chat-message-input")
                                          .children(".user-name")
                                          .val();
                    var userEmail = $(this).closest(".chat-message-input")
                                           .children(".user-email")
                                           .val();
                    
                    socket.send(JSON.stringify({
                                    "message": message, 
                                    "username": username,
                                    "userEmail": userEmail
                                }));
                                
                    $(this).val("");
                }
              });
    
    $(".request-chat").on("click", function() {
        var targetEmail = $(this).data("email");
        
        socket.send(JSON.stringify({
                        "message": "님이 대화를 요청했습니다. 수락하시겠습니까?",
                        "target": targetEmail,
                        "action": "INVITE"
                    }));
        
    });
});





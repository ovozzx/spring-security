$().ready(function() {
    
    var socket = new SockJS("/chat");
    
    socket.onopen = function() {
        // 소켓 엔드포인트에 정상적인 접근이 완료됨!
        var username = $(".chat").children(".chat-message-input")
                                 .children(".user-name")
                                 .val();
        var userEmail = $(".chat").children(".chat-message-input")
                                  .children(".user-email")
                                  .val();
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
            if ( confirm(chatData.message) ) {
                //alert("OK");
                socket.send(JSON.stringify({
                                "message": "",
                                "target": chatData.userEmail,
                                "action": "INVITE_OK"
                            }));
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
        else {
            
        }
        
        
        
        
        
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





/**
 * 
 */
$().ready(function() {
    $(".save-btn").on("click", function() {
        $("#requestRegistMemberVO").submit();
    })
    
    $("#email").on("keyup", function() {
        var value = $(this).val();
        
        var validateErrorCount = $(this).closest("div")
                                        .find(".validate-error")
                                        .not(".not-ok")
                                        .length;
        if (validateErrorCount > 0) {
            $(this).closest("div").find("span.not-ok").remove();
            $(this).closest("div").find("span.ok").remove();
            return;
        }
        
        var that = this;
        
		// CSRF 조회.
		var csrf = $("meta[name='_csrf']").attr("content");
		
		// {} 객체 리터럴이 파라미터로 전달됨 => 회원가입할때 마다 csrf를 보내겠다!
        $.get("/member/regist/check?email=" + value, {_csrf: csrf}, function(response) {
            var emailCount = response.body;
            if (emailCount === 0) {
                $(that).closest("div").find("span.not-ok").remove();
                
                var okCount = $(that).closest("div").find("span.ok").length;
                if (okCount === 0) {
                    $(that).after( $("<span class='ok'>사용 가능한 이메일입니다.</span>") );
                    autoActive();
                }
            }
            else {
                $(that).closest("div").find("span.ok").remove();
                
                var notOkCount = $(that).closest("div").find("span.not-ok").length;
                if (notOkCount === 0) {
                    $(that).after( $("<span class='validate-error not-ok'>이미 사용중인 이메일입니다.</span>") );
                    autoActive();
                }
            }
            
        });
        
    });
});





/**
 * 
 */
$().ready(function() {
    $(".save-btn").on("click", function() {
        $("#requestRegistMemberVO").submit();
    })
    
    $("#email").on("keyup", function() {
        var value = $(this).val();
        
        var validateErrorCount = $(this).closest("div").find(".validate-error").length;
        if (validateErrorCount > 0) {
            $(this).closest("div").find("span.not-ok").remove();
            $(this).closest("div").find("span.ok").remove();
            return;
        }
        
        var that = this;
        
        $.get("/member/regist/check?email=" + value, function(response) {
            var emailCount = response.body;
            if (emailCount === 0) {
                $(that).closest("div").find("span.not-ok").remove();
                
                var okCount = $(that).closest("div").find("span.ok").length;
                if (okCount === 0) {
                    $(that).after( $("<span class='ok'>사용 가능한 이메일입니다.</span>") );
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





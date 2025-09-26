/**
 * 
 */
$().ready(function() {
    $("label.require + div")
        .children("input, textarea")
        .on("keyup", function() {
            var value = $(this).val();
            if (value === "") {
                var errorLength = $(this).closest("div").children(".validate-require").length;
                
                if (errorLength === 0) {
                    $(this).after( $("<span class='validate-error validate-require'>필수 입력입니다.</span>") );
                }
            }
            else {
                $(this).closest("div").children(".validate-require").remove();
            }
            autoActive();
        });
        
    $("label.email + div")
            .children("input")
            .on("keyup", function() {
                var value = $(this).val();
                if (value !== "" && !/[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/g.test(value)) {
                    
                    var errorLength = $(this).closest("div").children(".validate-email").length;
                    if (errorLength === 0) {
                        $(this).after( $("<span class='validate-error validate-email'>이메일 형태로 작성하세요.</span>") );
                    }
                }
                else {
                    $(this).closest("div").children(".validate-email").remove();
                }
                autoActive();
            });
            
    $("label.password + div")
                .children("input")
                .on("keyup", function() {
                    var value = $(this).val();
                    
                    var pair = $(this).data("pair");
                    var pairValue = $(pair).val();
                    
                    if (value !== pairValue) {
                        var errorLength = $(this).closest("div").children(".validate-password").length;
                        errorLength += $(pair).closest("div").children(".validate-password").length;
                        
                        if (errorLength === 0) {
                            $(this).after( $("<span class='validate-error validate-password'>비밀번호가 일치하지 않습니다.</span>") );
                            $(pair).after( $("<span class='validate-error validate-password'>비밀번호가 일치하지 않습니다.</span>") );
                        }
                    }
                    else {
                        $(this).closest("div").children(".validate-password").remove();
                        $(pair).closest("div").children(".validate-password").remove();
                    }
                    autoActive();
                });

});

function autoActive() {
    var autoActive = $(".auto-active");
    if (autoActive) {
        var range = autoActive.data("range");
        var dependencies = autoActive.data("dependencies").split(",");
        
        for (var i = 0; i < dependencies.length; i++) {
            var dependency = dependencies[i];
            if ( $(dependency).val() === "" ) {
                autoActive.attr("disabled", "disabled");
                return;
            }
        }
        
        var errorLength = $(range).find(".validate-error").length;
        if (errorLength === 0) {
            autoActive.removeAttr("disabled");
        } else {
            autoActive.attr("disabled", "disabled");
        }
    }
}






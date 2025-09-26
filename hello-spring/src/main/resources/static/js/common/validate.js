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
                $(this).closest("div").children(".validate-error").remove();
            }
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
            });
});
/**
 * 
 */
$().ready(function() {
    
    $(".login-button").on("click", function() {
        
        $("#requestMemberLoginVO").submit();
        
    });
    
});
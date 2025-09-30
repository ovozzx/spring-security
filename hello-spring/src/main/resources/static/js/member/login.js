/**
 * 
 */
$().ready(function() {
    
    $(".login-button").on("click", function() {
        
        // 현재 URL을 체크.
        var currentUrl = window.location.pathname;
        // 로그인 버튼을 눌러서 로그인 페이지에 접근했을 경우.
        if (currentUrl === "/member/login") {
            // 로그인 이후에 이동할 페이지는 "게시글 목록"
            currentUrl = "/list"
        }
        
        $("#requestMemberLoginVO")
            .attr("action", "/member/login?nextUrl=" + currentUrl)
            .submit();
        
    });
    
});
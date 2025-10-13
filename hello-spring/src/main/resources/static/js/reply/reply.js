$().ready(function() {

    $(".reply-area").children(".reply-input")
                    .find(".save-btn")
                    .on("click", function() {
                        var replyInput = $(this).closest(".reply-input");
                        
                        // 1. 댓글로 등록할 파일이 있는지 확인.
                        var attachedFile = replyInput.find("input[type=file]");
                        console.log(attachedFile[0]);
                        
                        var files = attachedFile[0].files;
                        console.log(files);
                        
                        // 2. 댓글 내용이 있는지 확인.
                        var replyContent = replyInput.find("#reply-content").val();
                        console.log(replyContent);
                        replyContent = replyContent.trim();
                        replyInput.find("#reply-content").val(replyContent);
                        
                        if (replyContent === "") {
                            replyInput.find("#reply-content").trigger("keyup")
                            return;
                        }
                        
                        // 3. Ajax 전송 (/reply/게시글아이디)
                        // 첨부파일 데이터 + 댓글 내용 데이터
                        // 첨부파일을 전송시키기 위한 form-data
                        var formData = new FormData();
                        if (files.length > 0) {
                            formData.append("replyAttachFile", files[0]);
                        }
                        formData.append("replyContent", replyContent);
                        
                        var boardId = replyInput.closest(".reply-area").data("board-id");
                        
                        $.ajax({
                            url: "/reply/" + boardId,
                            method: "post",
                            data: formData,
                            enctype: "multipart/form-data",
                            processData: false,
                            // Content Type을 지정하지 않음.
                            // 아래 설정이 비어있을 경우 "application/x-www-urlencoded"로 전송되어 multipart를 전송하지 못함.
                            contentType: false, 
                            // success: Spring이 Ajax를 처리하고 반환된 값을 받아오는 코드.
                            success: function(response) {
                                // 4. 댓글 성공 여부를 반환.
                                console.log(response);
                            }
                        });
                    });
    
});
$().ready(function() {

    // 게시글 아이디 가져오기
    var boardId = $(".reply-area").data("board-id");
    
    // 댓글 조회 -> 계층조회
    $.get("/reply/" + boardId, function(response) {
        
        var replyTemplate = $(".reply-template").html();
        for (var i = 0; i < response.body.length; i++) {
            var reply = response.body[i];
            var replyHtml = 
                replyTemplate.replace("#replyId#", reply.replyId)
                             .replace("#replyAuthor#", reply.memberVO.name)
                             .replace("#replyCreateDateTime#", reply.crtDt)
                             .replace("#replyModifyDateTime#", reply.mdfyDt)
                             .replace("#replyRecommendCount#", reply.recommendCnt)
                             .replace("#replyContent#", reply.content);
            if (reply.fileGroupId) {
                replyHtml = replyHtml.replace("#fileGroupId#", reply.fileGroupId)
                                     .replace("#fileId#", reply.fileGroupVO.file[0].fileId)
                                     .replace("#replyAttachedFilename#", reply.fileGroupVO.file[0].fileDisplayName)
                                     .replace("#replyAttachedFileDownloadCount#", reply.fileGroupVO.file[0].fileDownloadCount);
            }
            
            var replyDom = $(replyHtml);
            if (!reply.fileGroupId) {
                replyDom.find(".attached-file").remove();
            }
            
            var whoami = $("#login-user-email").text();
            // 내가 작성한 댓글이라면 삭제하기, 수정하기, 댓글달기 만 보여준다.
            if (whoami === reply.memberVO.email) {
                replyDom.find(".reply-recommend").remove();
            }
            // 내가 작성한 댓글이 아니라면 추천하기, 댓글달기만 보여준다.
            else {
                replyDom.find(".reply-delete").remove();
                replyDom.find(".reply-modify").remove();
            }
            
            // 댓글에 댓글 달기
            replyDom.find(".reply-reply").on("click", function() {
                // 댓글 입력 창을 댓글 아래에 생성한다.
                alert("댓글달기!");
            });
            
            $(".replies").append(replyDom);
        }
    });
    
    // 댓글 추천하기 --> 본인이 작성한 댓글을 추천안되게.
    // 댓글 삭제하기 --> 본인이 작성한 댓글만 삭제하게.
    // 댓글 수정하기 --> 본인이 작성한 댓글만 수정하게.
    
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
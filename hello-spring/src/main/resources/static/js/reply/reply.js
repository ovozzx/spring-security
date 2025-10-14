String.prototype.replaceAll = function(findText, replaceText) {
    return this.split(findText).join(replaceText);
};

$().ready(function() {

    // 게시글 아이디 가져오기
    var boardId = $(".reply-area").data("board-id");
    
    // 댓글 조회 -> 계층조회
    $.get("/reply/" + boardId, function(response) {
        
        var replyTemplate = $(".reply-template").html();
        for (var i = 0; i < response.body.length; i++) {
            var reply = response.body[i];
            var replyHtml = 
                replyTemplate.replaceAll("#replyId#", reply.replyId)
                             .replaceAll("#replyAuthor#", reply.memberVO.name)
                             .replaceAll("#replyCreateDateTime#", reply.crtDt)
                             .replaceAll("#replyModifyDateTime#", reply.mdfyDt)
                             .replaceAll("#replyRecommendCount#", reply.recommendCnt)
                             .replaceAll("#replyContent#", reply.content);
            if (reply.fileGroupId) {
                replyHtml = replyHtml.replaceAll("#fileGroupId#", reply.fileGroupId)
                                     .replaceAll("#fileId#", reply.fileGroupVO.file[0].fileId)
                                     .replaceAll("#replyAttachedFilename#", reply.fileGroupVO.file[0].fileDisplayName)
                                     .replaceAll("#replyAttachedFileDownloadCount#", reply.fileGroupVO.file[0].fileDownloadCount);
            }
            
            var replyDom = $(replyHtml);
            if (!reply.fileGroupId) {
                replyDom.find(".attached-file").remove();
            }
            
            // 댓글 추천하기 --> 본인이 작성한 댓글을 추천안되게.
            replyDom.find(".reply-recommend").on("click", function() {
                var replyItem = $(this).closest("li");
                var replyId = replyItem.data("reply-id");
                $.get("/reply/" + replyId + "/recommend", function(response) {
                    // 추천하기 응답은 추천한 댓글의 최종 추천수.
                    replyItem.find(".recommend-count").text("추천: " + response.body);
                });
            });
            
            // 댓글 삭제하기 --> 본인이 작성한 댓글만 삭제하게.
            replyDom.find(".reply-delete").on("click", function() {
                var replyItem = $(this).closest("li");
                var replyId = replyItem.data("reply-id");
                $.get("/reply/" + replyId + "/delete", function(response) {
                    replyItem.remove();
                });
            });
            
            // 댓글 수정하기 --> 본인이 작성한 댓글만 수정하게.
            replyDom.find(".reply-modify").on("click", function() {
                var replyListItem = $(this).closest("li");
                
                $(".reply-input").insertAfter(replyListItem);
                $("#reply-content").val(replyListItem.find(".content").text());
                $(".reply-input").find(".reply-id").val( replyListItem.data("reply-id") );
                
                var attachedFileBox = replyListItem.find(".attached-file");
                var fileId = attachedFileBox.data("file-id");
                
                if (fileId) {
                    $(".reply-input").find(".modify-attached-file")
                                     .children(".delete-file")
                                     .val(fileId);
                                     
                    $(".reply-input").find(".modify-attached-file").show();
                    $(".reply-input").find(".modify-attached-file")
                                     .children(".attached-file-name")
                                     .text( attachedFileBox.children("a")
                                                           .text() );
                }
                else {
                    $(".reply-input").find(".modify-attached-file")
                                     .children(".delete-file")
                                     .val("");
                    $(".reply-input").find(".modify-attached-file").hide();
                }
                
                $("#reply-content").focus();
            });
            
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
                $(".reply-input").insertAfter($(this).closest("li"));
                $(".reply-input").find(".parent-reply-id").val($(this).closest("li").data("reply-id"));
            });
            
            replyDom.css({
                "margin-left": (reply.level - 1) * 20 + "px"
            });
            
            $(".replies").append(replyDom);
        }
    });
    
    // TODO 파일 다운로드 경로 수정. --> boardId ==> replyId
    
    
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
                        
                        // 댓글 수정일 경우, formData에 수정하려는 댓글의 아이디와
                        // 첨부파일 삭제 여부도 함께 전달한다.
                        var replyId = replyInput.find(".reply-id").val();
                        var deleteFileId = replyInput.find(".delete-file:checked").val();
                        if (replyId) {
                            replyInput.find(".reply-id").val("");
                            replyInput.find(".delete-file").val("");
                            replyInput.find(".modify-attached-file").hide();
                            replyInput.find(".delete-file").prop("checked", false);
                            replyInput.insertAfter($(".replies"));
                            formData.append("replyId", replyId);
                        }
                        if (deleteFileId) {
                            formData.append("deleteFileId", deleteFileId);
                        }
                        
                        
                        // 대댓글일 경우, formData에 상위 댓글 아이디를 첨부시킨다.
                        var parentReplyId = replyInput.find(".parent-reply-id").val();
                        if (parentReplyId) {
                            formData.append("parentReplyId", parentReplyId);
                            replyInput.find(".parent-reply-id").val("");
                            replyInput.insertAfter($(".replies"));
                        }
                        
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
                                window.location.reload();
                            }
                        });
                    });
    
});
$().ready(function() {
    var paginatorSearchForm = $(".paginator").data("search-form-class");
    var from = $(paginatorSearchForm).find(".from");
    var to = $(paginatorSearchForm).find(".to");
    
    if (from.length && to.length) {
        
        from.on("change", function() {
            // to에 선택한 날짜.
            var toDate = to.val();
            // from에 선택한 날짜.
            var fromDate = $(this).val();
            
            if (toDate) {
                // 2025-10-16 => 20251016
                var toDateNum = parseInt( toDate.replaceAll("-", "") );
                // 2025-10-17 => 20251017
                var fromDateNum = parseInt( fromDate.replaceAll("-", "") );
                
                if ( toDateNum < fromDateNum ) {
                    alert("From은 To보다 미래일 수 없습니다.");
                    from.val(toDate);
                }
            }
        });
        to.on("change", function() {
            // to에 선택한 날짜.
            var toDate = $(this).val();
            // from에 선택한 날짜.
            var fromDate = from.val();
            
            if (fromDate) {
                // 2025-10-16 => 20251016
                var toDateNum = parseInt( toDate.replaceAll("-", "") );
                // 2025-10-17 => 20251017
                var fromDateNum = parseInt( fromDate.replaceAll("-", "") );
                
                if ( toDateNum < fromDateNum ) {
                    alert("To는 From보다 과거일 수 없습니다.");
                    to.val(fromDate);
                }
            }
        });
        
    }
    
    $(paginatorSearchForm).find(".search-button")
                          .on("click", function() {
                            // form 안에 있는 select, input을 queryStringParameter로 만든다.!
                            var searchParam = $(this).closest(paginatorSearchForm)
                                                    .serialize();
                                                    
                            var listSize = $(".paginator")
                                                  .find(".page-list-size")
                                                  .val();
                            searchParam = "?" + searchParam + "&pageNo=0&listSize=" + listSize;
                            
                            window.location.href = window.location.pathname + searchParam;
                          });
    
    $(".paginator").find("a")
                   .attr("href", "javascript:void(0);")
                   .on("click", function() {
                    
                       var searchFormClass = $(this).closest(".paginator")
                                                    .data("search-form-class");
                       var searchParam = $(searchFormClass).serialize();
                    
                       var pageNo = $(this).data("page-no");
                       var listSize = $(this).closest(".paginator")
                                             .find(".page-list-size")
                                             .val();
                       window.location.href = window.location.pathname 
                                                + "?pageNo=" + pageNo
                                                + "&listSize=" + listSize
                                                + "&" + searchParam;
                   })
                   ;
    
    
    
    $(".paginator").find(".page-list-size")
                   .on("change", function() {
                    
                        var searchFormClass = $(this).closest(".paginator")
                                                     .data("search-form-class");
                        var searchParam = $(searchFormClass).serialize();
                    
                        window.location.href = 
                                    window.location.pathname 
                                    + "?pageNo=0&listSize=" + $(this).val()
                                    + "&" + searchParam;
                    
                   });
    
});
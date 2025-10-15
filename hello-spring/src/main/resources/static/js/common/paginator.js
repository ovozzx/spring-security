$().ready(function() {
    
    var paginatorSearchForm = $(".paginator").data("search-form-class");
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
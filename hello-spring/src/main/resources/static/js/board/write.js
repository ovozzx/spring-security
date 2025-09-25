/**
 * 
 */
$().ready(function() {
    $(".add-file").on("click", function() {
        var newFile = $("<input />");
        newFile.attr("type", "file");
        newFile.attr("name", "file");
        
        $(".vertical-flex").append(newFile);
    });
});
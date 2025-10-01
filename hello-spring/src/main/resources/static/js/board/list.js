$().ready(function() {

    $(".write-article").on("click", function() {
        window.location.href = "/write";
    });

    $(".download-article").on("click", function() {
        window.location.href = "/download-article";
    });

    $(".download-article-2").on("click", function() {
        window.location.href = "/download-article-2";
    });

});
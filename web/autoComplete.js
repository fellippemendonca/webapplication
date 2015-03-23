$(document).ready(function() {
    $(function() {
        $("#method").autocomplete({
            source: function(request, response) {
                $.ajax({
                    url: "ajaxautocompleteservlet",
                    type: "POST",
                    data: { term: request.term , field:"method" },
                    dataType: "json",
                    success: function(data) {
                        response(data);
                    }
               });
            }
        });
    });
});

$(document).ready(function() {
    $(function() {
        $("#environment").autocomplete({
            source: function(request, response) {
                $.ajax({
                    url: "ajaxautocompleteservlet",
                    type: "POST",
                    data: { term: request.term , field:"environment" },
                    dataType: "json",
                    success: function(data) {
                        response(data);
                    }
               });
            }
        });
    });
});

$(document).ready(function() {
    $(function() {
        $("#scheme").autocomplete({
            source: function(request, response) {
                $.ajax({
                    url: "ajaxautocompleteservlet",
                    type: "POST",
                    data: { term: request.term , field:"scheme" },
                    dataType: "json",
                    success: function(data) {
                        response(data);
                    }
               });
            }
        });
    });
});

$(document).ready(function() {
    $(function() {
        $("#host").autocomplete({
            source: function(request, response) {
                $.ajax({
                    url: "ajaxautocompleteservlet",
                    type: "POST",
                    data: { term: request.term , field:"host" },
                    dataType: "json",
                    success: function(data) {
                        response(data);
                    }
               });
            }
        });
    });
});

$(document).ready(function() {
    $(function() {
        $("#path").autocomplete({
            source: function(request, response) {
                $.ajax({
                    url: "ajaxautocompleteservlet",
                    type: "POST",
                    data: { term: request.term , field:"path" },
                    dataType: "json",
                    success: function(data) {
                        response(data);
                    }
               });
            }
        });
    });
});


$(document).ready(function() {

    $("#submit-request-info").click(function() {
        $("body").toggleClass("wait");
        var jsonRequestObj = fillRequestObject();
        if (jsonRequestObj === null) {
            //do nothing
        } else {
            $.post('restexecutorservlet', {"jsonRequestObj": jsonRequestObj},
            function(resp) { // on success
                $("body").toggleClass("wait");
                printResponse(resp);
            })
                    .fail(function() { //on failure
                        $("body").toggleClass("wait");
                        alert("Falha ao executar o request!!");
                    });
        }
    });

    $("#insert-request-info").click(function() {
        var jsonRequestObj = fillRequestObject();
        if (jsonRequestObj === null) {
            //do nothing
        } else {
            $.post('requesthandlerservlet', {"jsonRequestObj": jsonRequestObj, operation: "insert"},
            function(resp) { // on success
                alert(resp);
                elementClear();
                RequestTableData('tag-array');
            })
                    .fail(function() { //on failure
                        alert("Falha inserir o novo request!");
                    });
        }
    });

    $("#update-request-info").click(function() {
        var jsonRequestObj = fillRequestObject();
        if (jsonRequestObj === null) {
            //do nothing
        } else {
            $.post('requesthandlerservlet', {"jsonRequestObj": jsonRequestObj, operation: "update"},
            function(resp) { // on success
                alert(resp);
                elementClear();
                RequestTableData('tag-array');
            })
                    .fail(function() { //on failure
                        alert("Falha ao atualizar o request!");
                    });
        }
    });

    $("#remove-request-info").click(function() {
        var jsonRequestObj = fillRequestObject();
        if (jsonRequestObj === null) {
            //do nothing
        } else {
            $.post('requesthandlerservlet', {"jsonRequestObj": jsonRequestObj, operation: "remove"},
            function(resp) { // on success
                alert(resp);
                elementClear();
                RequestTableData('tag-array');
            })
                    .fail(function() { //on failure
                        alert("Falha ao remover o request!");
                    });
        }
    });
});




function fillRequestObject() {
    var id = $("#request-id").val();
    if ($("#request-id").val() === "") {
        id = "0";
    } else {
        id = $("#request-id").val();
    }
    
    
    var Environment = $("#environment").val();
    var Method = $("#method").val();
    var Scheme = $("#scheme").val();
    var Host = $("#host").val();
    var Path = $("#path").val();

    var Payload;
    if ($("#Payload").val() === "") {
        Payload = "";
    } else {
        Payload = $("#Payload").val();
    }

    //-----Arrays
    var Headers = new Array();
    populateDualArray(Headers, "HeaderName", "HeaderValue");

    var Templates = new Array();
    populateArray(Templates, "TemplateName");

    var Parameters = new Array();
    populateDualArray(Parameters, "ParameterName", "ParameterValue");

    var Tags = new Array();
    //Tags = $("#request-tags").tagit("assignedTags");
    populateTagArray(Tags, "request-tags");
    //populateTagList(Tags, "request-tags");
    //-----

    if (Environment === "" || Method === "" || Scheme === "" || Host === "" || Path === "") {
        alert("Dados insuficientes para realizar o request!");
        return null;
    } else {
        var RequestObj = {
            dbId: id
            , environment: Environment
            , method: Method
            , scheme: Scheme
            , host: Host
            , path: Path
            , payload: Payload
            , templates: Templates
            , headers: Headers
            , parameters: Parameters
            , jsonTags: Tags};

        var jsonRequestObj = JSON.stringify(RequestObj);
        return jsonRequestObj;
    }
}

function populateArray(arrayToFill, input) {
    var arr = new Array();
    arr = document.getElementsByName(input);
    for (var i = 0; i < arr.length; i++)
    {
        var obj = document.getElementsByName(input).item(i);
        arrayToFill.push(obj.value);
    }
}

function populateDualArray(arrayToFill, input1, input2) {
    var arr1 = new Array();
    arr1 = document.getElementsByName(input1);
    for (var i = 0; i < arr1.length; i++)
    {
        var obj1 = document.getElementsByName(input1).item(i);
        var obj2 = document.getElementsByName(input2).item(i);
        var value1 = obj1.value;
        var value2 = obj2.value;
        var h = {name: value1, value: value2};
        arrayToFill.push(h);
    }
}

function populateTagArray(arrayToFill, tagElement) {
    var arr1 = new Array();
    arr1 = $("#"+tagElement).tagit("assignedTags");
    if (arr1.length > 0) {
        $.each(arr1, function(i, tag) {
            var h = {id: 0, name: tag};
            arrayToFill.push(h);
        });
    }
}

function printResponse(json) {
    var request = json.request;
    var responseStatus = json.status;
    var responseContents = JSON.stringify(JSON.parse(json.contents),null,4);  

    var responseTable = "<table id='responseTable1' class='table table-bordered' cellspacing='0' width='100%'>";
    responseTable += "<thead><tr><th>Request</th><th>Response Status</th></th></tr></thead><tbody>";
    responseTable += "<tr><td>" + request + "</td><td>" + responseStatus + "</td></tr>";
    responseTable += "</tbody></table>";

    responseTable += "<br><h4>Response Contents:</h4><br>";
    responseTable += "<div id='responseContents-div'>"+syntaxHighlight(responseContents)+"</div>";

    $("#response-div").empty();
    $("#response-div").append(responseTable);
}

function syntaxHighlight(json) {
    if (typeof json != 'string') {
         json = JSON.stringify(json, undefined, 2);
    }
    json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
        var cls = 'number';
        if (/^"/.test(match)) {
            if (/:$/.test(match)) {
                cls = 'key';
            } else {
                cls = 'string';
            }
        } else if (/true|false/.test(match)) {
            cls = 'boolean';
        } else if (/null/.test(match)) {
            cls = 'null';
        }
        return '<span class="' + cls + '">' + match + '</span>';
    });
}
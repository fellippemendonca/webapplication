
$(document).ready(function() {

    $("#submit-request-info").click(function() {
        //Trava cursor após preencher o response
        $("body").toggleClass("wait");
        var jsonRequestObj = fillRequestObject();
        if (jsonRequestObj === null) {
            //Destrava cursor após preencher o response
            $("body").toggleClass("wait");
        } else {
            $.post('restexecutorservlet', {"jsonRequestObj": jsonRequestObj},
            function(resp) { // on success
                printResponse(resp);
            })
                    .fail(function() { //on failure
                        //Destrava cursor após preencher o response
                        $("body").toggleClass("wait");
                        alert("Request failed.");
                    });
        }
    });
});
/*-----------------------------------------------------------------------------*/

function insertRequestOnDatabase() {
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
                    alert("Failed to insert the new request!");
                });
    }
}


function updateRequestFromDatabase() {
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
                    alert("Failed to update the selected request!");
                });
    }
}

function deleteRequestFromDatabase() {
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
                    alert("Failed to delete the selected request!");
                });
    }
}

/*----------------------------------------------------------------------------*/


function fillRequestObject() {
    var id = $("#request-id").val();
    if ($("#request-id").val() === "") {
        id = "0";
    } else {
        id = $("#request-id").val();
    }

    var RequestName = $("#requestName").val();
    var RequestDescription = $("#requestDescription").val();
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

    /*----------------------------DYNAMIC REQUEST FILLER----------------------*/
    var DynamicData;
    if ($("#dynamic-data-query").val() == "") {
        DynamicData = null;
    } else {
        DynamicData = {
            idDynamicInputData: 0,
            idRequestReference: id,
            requestType: "SQL",
            jsonRequest: JSON.stringify({databaseName: $("#databank-selector").val(),request: $("#dynamic-data-query").val()}, undefined, 2)
        };
    }
    /*------------------------------------------------------------------------*/

    //-----Arrays
    var Headers = new Array();
    populateDualArray(Headers, "HeaderName", "HeaderValue");

    var Templates = new Array();
    populateArray(Templates, "TemplateName");

    var Parameters = new Array();
    populateDualArray(Parameters, "ParameterName", "ParameterValue");

    var Tags = new Array();
    populateTagArray(Tags, "request-tags");

    if (RequestName === "" || Environment === "" || Method === "" || Scheme === "" || Host === "" || Path === "") {
        alert("Please fill all necessary fields before execute the request!");
        return null;
    } else {
        var RequestObj = {
            dbId: id
            , requestName: RequestName
            , requestDescription: RequestDescription
            , environment: Environment
            , method: Method
            , scheme: Scheme
            , host: Host
            , path: Path
            , payload: Payload
            , jsonDynamicData: DynamicData
            , templates: Templates
            , headers: Headers
            , parameters: Parameters
            , jsonTags: Tags};

        var jsonRequestObj = JSON.stringify(RequestObj);
        return jsonRequestObj;
    }
}

//Popula Array recebido com a lista de elementos que tem o mesmo nome do InputField
function populateArray(arrayToFill, input) {
    var arr = new Array();
    arr = document.getElementsByName(input);
    for (var i = 0; i < arr.length; i++)
    {
        var obj = document.getElementsByName(input).item(i);
        arrayToFill.push(obj.value);
    }
}

//Popula Array recebido com a lista de elementos que tem o mesmo nome do InputField
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

//Recebe um array e o preenche de TAGs Objects presentes no inputField
function populateTagArray(arrayToFill, tagElement) {
    var arr1 = new Array();
    arr1 = $("#" + tagElement).tagit("assignedTags");
    if (arr1.length > 0) {
        $.each(arr1, function(i, tag) {
            var h = {id: 0, name: tag};
            arrayToFill.push(h);
        });
    }
}

//Recebe objeto response contents e disponibiliza visualizaçao formatada no response-div
function printResponse(json) {

    var request = json.request;
    var responseStatus = json.status;
    var endDate = json.endDate;
    var execTime = json.execTime;
    var responseContents = "";

    //Trata casos em que o response não é um json válido.
    if (isJson(json.contents)) {
        responseContents = JSON.stringify(JSON.parse(json.contents), null, 4);
    } else {
        responseContents = formatXml(json.contents);
    }

    var responseTable = "<table id='responseTable' class='table table-bordered' cellspacing='0' width='100%'>";
    responseTable += "<thead><tr><th>Request</th><th>Response Status</th><th>Time</th><th>Date</th></tr></thead><tbody>";
    responseTable += "<tr><td>" + request + "</td><td>" + responseStatus + "</td><td>" + execTime + " ms</td><td>" + endDate + "</td></tr>";
    responseTable += "</tbody></table><br>";

    var responseContentsDiv = document.createElement('div');
    responseContentsDiv.id = "responseContents-div";
    responseContentsDiv.innerHTML = syntaxHighlight(responseContents);

    var responseContentsPanel = generatePanel("Response Contents", responseContentsDiv);

    var selectAll = document.createElement('button');
    selectAll.id = "selectAll";
    selectAll.className = 'btn btn-primary btn';
    selectAll.innerHTML = "selectAll";
    //selectAll.setAttribute("onClick", "selectOnClick("+responseContentsDiv.id+");");

    $("#response-div").empty();
    $("#response-div").append(responseTable);
    $("#response-div").append(responseContentsPanel);
    //$("#response-div").append(selectAll);

    //Destrava cursor após preencher o response
    $("body").toggleClass("wait");
    //Scroll into position
    document.getElementById("response-view-div").scrollIntoView();
}

//Verifica se a string pode ser parseada como um Json válido.
function isJson(str) {
    try {
        JSON.parse(str);
    } catch (e) {
        return false;
    }
    return true;
}

//Formata Json de forma que fique mais legível para o User 
function syntaxHighlight(json) {
    if (typeof json != 'string') {
        json = JSON.stringify(json, undefined, 2);
    }
    json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function(match) {
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

function generatePanel(title, childNode) {

    var defaultPanel = document.createElement('div');
    defaultPanel.className = "panel panel-default";

    var panelHeader = document.createElement('div');
    panelHeader.className = "panel-heading";
    panelHeader.innerHTML = title;

    var panelBody = document.createElement('div');
    panelBody.id = "responseContentsDiv001";
    panelBody.className = "panel-body";
    panelBody.appendChild(childNode);

    defaultPanel.appendChild(panelHeader);
    defaultPanel.appendChild(panelBody);

    return defaultPanel;
}

function selectOnClick(divId) {
    document.getElementById(divId).select();
}

function formatXml(xml) {
    var formatted = '';
    var reg = /(>)(<)(\/*)/g;
    xml = xml.replace(reg, '$1\r\n$2$3');
    var pad = 0;
    jQuery.each(xml.split('\r\n'), function(index, node) {
        var indent = 0;
        if (node.match( /.+<\/\w[^>]*>$/ )) {
            indent = 0;
        } else if (node.match( /^<\/\w/ )) {
            if (pad != 0) {
                pad -= 1;
            }
        } else if (node.match( /^<\w[^>]*[^\/]>.*$/ )) {
            indent = 1;
        } else {
            indent = 0;
        }

        var padding = '';
        for (var i = 0; i < pad; i++) {
            padding += '  ';
        }

        formatted += padding + node + '\r\n';
        pad += indent;
    });

    return formatted;
}
//"document.getElementById('responseContents-div').select();"
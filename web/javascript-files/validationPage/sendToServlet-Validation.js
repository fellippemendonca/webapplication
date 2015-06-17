
$(document).ready(function() {

    /*---------EXECUTA SCENARIO DE VALIDAÇÃO -------------------------------------*/
    $("#execute-validation-button").click(function() {
        //Trava cursor após preencher o response
        $("body").toggleClass("wait");
        var jsonValidationObj = fillValidationScenarioObject();
        //alert(jsonValidationObj);
        if (jsonValidationObj === null) {
            //Destrava cursor após preencher o response
            $("body").toggleClass("wait");
        } else {
            $.post('ValidationScenarioExecutorServlet', {"jsonValidationObj": jsonValidationObj},
            function(resp) { // on success
                //alert(resp);
                printValidationResponse(resp);
            })
                    .fail(function() { //on failure
                        //Destrava cursor após preencher o response
                        $("body").toggleClass("wait");
                        alert("Falha ao executar validação!");
                    });
        }
    });

    $("#insert-validation-button").click(function() {
        var jsonValidationObj = fillValidationScenarioObject();
        if (jsonValidationObj === null) {
            //do nothing
        } else {
            $.post('ValidationScenarioHandlerServlet', {"jsonValidationObj": jsonValidationObj, operation: "insert"},
            function(resp) { // on success
                alert(resp);
                var requestId = $("#request-id").val();
                //elementClear();
                getValidationScenarioList('RequestValidationFilter', requestId);
            })
                    .fail(function() { //on failure
                        alert("Failed to insert the new scenario!");
                    });
        }
    });

    $("#update-validation-button").click(function() {
        var jsonValidationObj = fillValidationScenarioObject();
        if (jsonValidationObj === null) {
            //do nothing
        } else {
            $.post('ValidationScenarioHandlerServlet', {"jsonValidationObj": jsonValidationObj, operation: "update"},
            function(resp) { // on success
                alert(resp);
                var requestId = $("#request-id").val();
                //elementClear();
                getValidationScenarioList('RequestValidationFilter', requestId);
            })
                    .fail(function() { //on failure
                        alert("Failed to update the selected scenario!");
                    });
        }
    });

    $("#delete-validation-button").click(function() {
        var jsonValidationObj = fillValidationScenarioObject();
        if (jsonValidationObj === null) {
            //do nothing
        } else {
            $.post('ValidationScenarioHandlerServlet', {"jsonValidationObj": jsonValidationObj, operation: "remove"},
            function(resp) { // on success
                alert(resp);
                var requestId = $("#request-id").val();
                //elementClear();
                getValidationScenarioList('RequestValidationFilter', requestId);
            })
                    .fail(function() { //on failure
                        alert("Failed to delete the selected scenario!");
                    });
        }
    });

    $("#execute-massive-validation-button").click(function() {
        getValidationScenarioList('ExecuteRequestValidation', getJsonRequestValidation().idRequestReference);
    });

});

function fillRequestObject() {
    var id = $("#request-id").val();
    if ($("#request-id").val() === "") {
        id = "0";
    } else {
        id = $("#request-id").val();
    }

    var RequestName = $("#requestName").val();
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
    populateTagArray(Tags, "request-tags");

    if (RequestName === "" || Environment === "" || Method === "" || Scheme === "" || Host === "" || Path === "") {
        alert("Please fill all necessary fields before execute the request!");
        return null;
    } else {
        var RequestObj = {
            dbId: id
            , requestName: RequestName
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

//Recebe um array e o preenche de Validation Elements presentes no inputField
function populateValidationArray(arrayToFill, input) {
    var arr = new Array();
    arr = document.getElementsByName(input + "Element");
    for (var i = 0; i < arr.length; i++)
    {
        var operation = document.getElementsByName(input + "Element").item(i);
        var content = document.getElementsByName(input + "Value").item(i);
        var JsonValidationOperation = {
            idValidationOperation: 0,
            name: operation.title,
            description: ""
        };

        /*if (operation.title === 'Compare Json Structure') {
         document.getElementsById(input + "Value" + i).item(i);
         } else {*/
        var value2 = content.value;
        //}

        var h = {
            idResponseValidationElement: 0,
            idValidationScenario: 0,
            jsonValidationOperation: JsonValidationOperation,
            expectedObject: value2,
            success: false
        };
        arrayToFill.push(h);
    }
}

function fillValidationScenarioObject() {
    var validationScenarioId;
    if (getJsonValidationScenario() === null) {
        validationScenarioId = 0;
    } else {
        validationScenarioId = getJsonValidationScenario().idValidationScenario;
    }

    var id = $("#request-id").val();
    var Elements = new Array();
    populateValidationArray(Elements, "Validation");
    var Description = $("#scenario-description").val();
    var jsonValidationObj = fillRequestObject();
    var ValidationObj = {
        idValidationScenario: validationScenarioId,
        idRequestReference: id,
        requestObject: jsonValidationObj,
        validationScenarioDescription: Description,
        jsonValidationElementList: Elements,
        success: false
                //responseContents:
    };
    var ValidationScenario = JSON.stringify(ValidationObj);
    return ValidationScenario;
}

function printValidationResponse(json) {
    refillValidationList(json);
    var request = json.responseContents.request;
    var responseStatus = json.responseContents.status;
    var endDate = json.responseContents.endDate;
    var execTime = json.responseContents.execTime;
    var responseContents = "";

    //Trata casos em que o response não é um json válido.
    if (isJson(json.responseContents.contents)) {
        responseContents = JSON.stringify(JSON.parse(json.responseContents.contents), null, 4);
    } else {
        responseContents = json.responseContents.contents;
    }

    var responseTable = "<br/><h4>API Response:</h4><br/><table id='responseTable' class='table table-bordered' cellspacing='0'>";
    responseTable += "<thead><tr><th>Response Status</th><th>Time</th><th>Date</th></tr></thead><tbody>";
    responseTable += "<tr><td>" + responseStatus + "</td><td>" + execTime + " ms</td><td>" + endDate + "</td></tr>";
    responseTable += "</tbody></table>";

    var responseContentsDiv = document.createElement('div');
    responseContentsDiv.id = "responseContents-div";
    responseContentsDiv.innerHTML = syntaxHighlight(responseContents);

    var responseContentsPanel = generatePanel("Response Contents", responseContentsDiv);

    var selectAll = document.createElement('button');
    selectAll.id = "selectAll";
    selectAll.className = 'btn btn-primary btn';
    selectAll.innerHTML = "selectAll";
    selectAll.setAttribute("onClick", "selectOnClick("+responseContentsDiv.id+");");

    $("#response-scenario-div").empty();
    $("#response-scenario-div").append(responseTable);
    $("#response-scenario-div").append(responseContentsPanel);
    //$("#response-div").append(selectAll);

    //Destrava cursor após preencher o response
    $("body").toggleClass("wait");
}

function generatePanel(title, childNode){
    
    var defaultPanel = document.createElement('div');
    defaultPanel.id ="responseContentsDiv001";
    defaultPanel.className = "panel panel-default";
    
    var panelHeader = document.createElement('div');
    panelHeader.className = "panel-heading";
    panelHeader.innerHTML = title;
    
    var panelBody = document.createElement('div');
    panelBody.className = "panel-body"; 
    panelBody.appendChild(childNode);
    
    defaultPanel.appendChild(panelHeader);
    defaultPanel.appendChild(panelBody);
    
    return defaultPanel;
}

function refillValidationList(json) {
    ValidationClear();
    document.getElementById('scenario-description').value = json.validationScenarioDescription;
    var elementList = json.jsonValidationElementList;
    $.each(elementList, function(i, element) {
        var ledColor = "red";
        if (element.success === true) {
            ledColor = "green";
        }
        addValidationElement('Validation', element.jsonValidationOperation.name, element.expectedObject, element.jsonValidationOperation.name, ledColor);
    });
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


/*----------------------------------------------------------------------------*/

function updateValidationScenario() {
    var jsonValidationObj = fillValidationScenarioObject();
    if (jsonValidationObj === null) {
        //do nothing
    } else {
        $.post('ValidationScenarioHandlerServlet', {"jsonValidationObj": jsonValidationObj, operation: "update"},
        function(resp) { // on success
            alert(resp);
            var requestId = $("#request-id").val();
            elementClear();
            getValidationScenarioList('RequestValidationFilter', requestId);
        })
                .fail(function() { //on failure
                    alert("Failed to update the selected scenario!");
                });
    }
}

function deleteValidationScenario() {
    var jsonValidationObj = fillValidationScenarioObject();
    if (jsonValidationObj === null) {
        //do nothing
    } else {
        $.post('ValidationScenarioHandlerServlet', {"jsonValidationObj": jsonValidationObj, operation: "remove"},
        function(resp) { // on success
            alert(resp);
            var requestId = $("#request-id").val();
            elementClear();
            getValidationScenarioList('RequestValidationFilter', requestId);
        })
                .fail(function() { //on failure
                    alert("Failed to delete the selected scenario!");
                });
    }
}


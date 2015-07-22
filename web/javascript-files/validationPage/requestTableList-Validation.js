// When the page is fully loaded...
/* RequestTableData(tagElementId); //Preenche tabela tendo como base a lista de tags pela qual deve utilizar como filtro
 * 
 */


function getJsonRequestValidation() {
    return jsonRequestValidation;
}
function setJsonRequestValidation(json) {
    jsonRequestValidation = json;
}

function getJsonValidationScenario() {
    return jsonValidationScenario;
}
function setJsonValidationScenario(json) {
    jsonValidationScenario = json;
}

$(document).ready(function() {
    RequestTableData('tag-array');

    $("#submit-tag-filter").click(function() {
        $("#request-list").empty();
        elementClear();
        RequestTableData('tag-array');
    });

});


/*Função que busca lista de requests e filtra exibição do resultado com base na lista de tags*/
function RequestTableData(tagElementId) {
    //elementClear();
    var filter = new Array();
    if (document.getElementById(tagElementId).value != "") {
        filter = document.getElementById(tagElementId).value.split(",").map(String);
    }

    $.get('requestfilter', {"tag_array": filter},
    function(resp) {
        var requestList = resp;
        var tableshow = "<table id='requestTable' class='table table-bordered' cellspacing='0' width='100%'>";
        tableshow += "<thead><tr><th>id</th><th>Env</th><th>Name</th><th>Path</th></tr></thead><tbody>";

        $.each(requestList, function(i, request) {
            if (compareLists(filter, getNameList(request))) {
                tableshow += "<tr><td>" + i + "</td><td>" + request.environment + "</td><td>" + request.requestName + "</td><td>" + request.path + "</td></tr>";
            }
        });

        tableshow += "</tbody></table>";
        $("#request-list").empty()
                .append("<br>");
        $("#request-list").append(tableshow);


        Synthesizer(requestList);
    })
            .fail(function() { // on failure
                alert("Request failed.");
            });
}
/*----------------------------------------------------------------------------*/

/*--Função que detecta linha da tabela clicada e preenche os campos do request*/
function Synthesizer(list) {
    var requestList = list;
    var table = document.getElementById("requestTable");
    var thead = table.getElementsByTagName("thead")[0];
    var tbody = table.getElementsByTagName("tbody")[0];
    var ishigh;


    tbody.onclick = function(e) {
        e = e || window.event;
        var td = e.target || e.srcElement;
        var row = td.parentNode;
        if (ishigh && ishigh != row) {
            ishigh.className = '';
        }
        row.className = row.className === "highlighted" ? "" : "highlighted";
        ishigh = row;
        document.getElementById('request-name-indicator').value = requestList[row.cells[0].innerHTML].requestName;
        document.getElementById('request-env-indicator').value = requestList[row.cells[0].innerHTML].environment;
        populateFields(row);

    };

    function el(id) {
        return document.getElementById(id);
    }


    function populateFields(row) {
        requestClear();
        el('request-id').value = requestList[row.cells[0].innerHTML].dbId;
        el('requestName').value = requestList[row.cells[0].innerHTML].requestName;
        el('method').value = requestList[row.cells[0].innerHTML].method;
        el('environment').value = requestList[row.cells[0].innerHTML].environment;
        el('scheme').value = requestList[row.cells[0].innerHTML].scheme;
        el('host').value = requestList[row.cells[0].innerHTML].host;
        el('path').value = requestList[row.cells[0].innerHTML].path;
        el('Payload').value = requestList[row.cells[0].innerHTML].payload;

        /*------------------------DYNAMIC DATA--------------------------------*/
        if(requestList[row.cells[0].innerHTML].hasOwnProperty('jsonDynamicData')){
            if(requestList[row.cells[0].innerHTML].jsonDynamicData!==null){
                el('databank-selector').value = JSON.parse(requestList[row.cells[0].innerHTML].jsonDynamicData.jsonRequest).databaseName;
                el('dynamic-data-query').value = JSON.parse(requestList[row.cells[0].innerHTML].jsonDynamicData.jsonRequest).request;
            } else{
                el('databank-selector').value = null;
                el('dynamic-data-query').value = null;
            }
        }
        /*--------------------------------------------------------------------*/

        var templateList = requestList[row.cells[0].innerHTML].templates;
        $.each(templateList, function(i, template) {
            addElement('Template', template);
        });

        var headerList = requestList[row.cells[0].innerHTML].headers;
        $.each(headerList, function(i, header) {
            addElement2('Header', header.name, header.value);
        });

        var parameterList = requestList[row.cells[0].innerHTML].parameters;
        $.each(parameterList, function(i, parameter) {
            addElement2('Parameter', parameter.name, parameter.value);
        });

        var tagList = requestList[row.cells[0].innerHTML].jsonTags;
        $.each(tagList, function(i, tag) {
            $('#request-tags').tagit('createTag', tag.name);
        });
        document.getElementById("validation-view-div").scrollIntoView();
        document.getElementById('create-new-validation-button').className = 'btn btn-primary btn-lg';
        document.getElementById('execute-massive-validation-button').className = 'btn btn-primary';
        getValidationScenarioList('RequestValidationFilter', requestList[row.cells[0].innerHTML].dbId);
    }
}
/*----------------------------------------------------------------------------*/

/*-------Função que filtra requests por tag.----------------------------------*/
function compareLists(list1, list2) {
    var containsAll = true;
    $.each(list1, function(i, element1) {
        var containsOne = false;
        $.each(list2, function(i, element2) {
            if (String(element1) == String(element2)) {
                containsOne = true;
            }
        });
        if (containsOne !== true) {
            containsAll = false;
        }
    });
    return containsAll;
}

function getNameList(request) {
    var idList = new Array();
    $.each(request.jsonTags, function(i, tag) {
        idList.push(tag.name);
    });
    return idList;
}
/*----------------------------------------------------------------------------*/

function getValidationScenarioList(url, busca) {
    var dataOutput;
    $.get(url, {"requestId": busca}, function(data) {
        pospulateRequestValidationList(data);
    }, "json")
            .fail(function() { //on failure
                alert("Failed to retrieve information from servlet" + url);
            });
    return dataOutput;
}

function pospulateRequestValidationList(json) {
    jsonRequestValidation = json;
    removeElement("scheduleButton");
    removeInnerElement('validation-list-div');
    RequestValidationCounter = 0;
    var validationScenarioList = new Array();
    validationScenarioList = jsonRequestValidation.jsonValidationScenarioList;
    if (validationScenarioList.length > 0) {
        /*-------------------------scheduleButton-----------------------------*/
        document.getElementById('schedule-button').className = 'btn btn-default dropdown-toggle';
        isScheduled(jsonRequestValidation.idRequestReference);
        /*--------------------------------------------------------------------*/
        $.each(validationScenarioList, function(i, scenario) {
            var newdiv1 = document.createElement('div');
            newdiv1.id = 'validation-list-div' + 'Row' + i;
            newdiv1.className = "row";
            document.getElementById('validation-list-div').appendChild(newdiv1);
            addValidationScenario(newdiv1.id, scenario.idValidationScenario, scenario.validationScenarioDescription, scenario.success);
        });
    } else {
        document.getElementById('schedule-button').className = 'btn btn-default dropdown-toggle disabled';
        //alert("Nenhum cenário cadastrado!");
    }
}
/*------------------PREENCHER DADOS DE VALIDAÇÃO------------------------------*/

function validationCreate() {
    //populateValidationFields(getJsonRequestValidation().idRequestReference);
    setJsonValidationScenario(null);
    removeInnerElement('response-scenario-div');
    removeElementValue('scenario-description');
    removeInnerElement('Validation');
    document.getElementById('update-validation-button').className = 'btn btn-warning disabled';
    document.getElementById('delete-validation-button').className = 'btn btn-danger disabled';
    $('#myModal').modal('show');
    //document.getElementById("validation-view-div").scrollIntoView();

}

function validationEdit(row) {
    populateValidationFields(row);
    document.getElementById('update-validation-button').className = 'btn btn-warning';
    document.getElementById('delete-validation-button').className = 'btn btn-danger';
    $('#myModal').modal('show');
    //document.getElementById("validation-view-div").scrollIntoView();
}

function el(id) {
    return document.getElementById(id);
}

function populateValidationFields(row) {
    requestClear();
    setJsonValidationScenario(getJsonRequestValidation().jsonValidationScenarioList[row]);
    var requestObj = JSON.parse(getJsonValidationScenario().requestObject);

    el('request-id').value = getJsonValidationScenario().idRequestReference;
    el('method').value = requestObj.method;
    el('environment').value = requestObj.environment;
    el('scheme').value = requestObj.scheme;
    el('host').value = requestObj.host;
    el('path').value = requestObj.path;
    el('Payload').value = requestObj.payload;

    /*------------------------DYNAMIC DATA--------------------------------*/
    if (requestObj.hasOwnProperty('jsonDynamicData')) {
        if(requestObj.jsonDynamicData!==null){
            el('databank-selector').value = JSON.parse(requestObj.jsonDynamicData.jsonRequest).databaseName;
            el('dynamic-data-query').value = JSON.parse(requestObj.jsonDynamicData.jsonRequest).request;
        } else {
            el('databank-selector').value = null;
            el('dynamic-data-query').value = null;
        } 
    }
    /*--------------------------------------------------------------------*/

    var templateList = requestObj.templates;
    $.each(templateList, function(i, template) {
        addElement('Template', template);
    });

    var headerList = requestObj.headers;
    $.each(headerList, function(i, header) {
        addElement2('Header', header.name, header.value);
    });

    var parameterList = requestObj.parameters;
    $.each(parameterList, function(i, parameter) {
        addElement2('Parameter', parameter.name, parameter.value);
    });

    var tagList = requestObj.jsonTags;
    $.each(tagList, function(i, tag) {
        $('#request-tags').tagit('createTag', tag.name);
    });

    el('scenario-description').value = getJsonValidationScenario().validationScenarioDescription;

    var elementList = getJsonValidationScenario().jsonValidationElementList;
    $.each(elementList, function(i, element) {
        addValidationElement('Validation', element.jsonValidationOperation.name, element.expectedObject, element.jsonValidationOperation.name, 'red');
    });

    document.getElementById('create-new-validation-button').className = 'btn btn-primary btn-lg';
    document.getElementById('execute-massive-validation-button').className = 'btn btn-primary';

}





/***************************FERRAMENTAS DE TESTE*******************************/
function simpleRequest(url, busca) {
    var dataOutput;
    $.get(url, {"requestId": busca}, function(data) {
        printResponse(data);
    }, "json")
            .fail(function() { //on failure
                alert("Failed to retrieve information from servlet" + url);
            });
    return dataOutput;
}

function printResponse(json) {
    var request = json.jsonValidationScenarioList[1].validationScenarioDescription;
    alert(request.toString());
}

function getIdList(request) {
    var idList = new Array();
    $.each(request.jsonTags, function(i, tag) {
        idList.push(tag.id);
    });
    return idList;
}

function isScheduled(requestId) {
    $.get("ScheduledRequests", {"requestId": "busca"}, function(data) {
        var isItScheduled = false;
        //var requestList = JSON.parse(data).requestList;
        var requestList = data;
        $.each(requestList, function(i, request) {
            if (request.dbId == requestId) {
                isItScheduled = true;
                var scheduleButton = document.createElement('button');
                scheduleButton.id = "scheduleButton";
                scheduleButton.className = 'btn btn-success';
                scheduleButton.innerHTML = "Scheduled";
                document.getElementById('schedule-validation').appendChild(scheduleButton);
            }
        });
        return isItScheduled;
    }, "json")
            .fail(function() { //on failure
                alert("Failed to retrieve information from servlet " + "ScheduledRequests");
            });
}



function insertSchedule() {
    //alert (getJsonRequestValidation().idRequestReference);
    $.post('ScheduledRequestHandlerServlet', {"idRequestReference": getJsonRequestValidation().idRequestReference, "operation": "insert"},
    function(resp) { // on success
        alert(resp);
        //elementClear();
        getValidationScenarioList('RequestValidationFilter', getJsonRequestValidation().idRequestReference);
    })
            .fail(function() { //on failure
                alert("Failed during scenario schedule!");
            });

}

function removeSchedule() {
    //alert (getJsonRequestValidation().idRequestReference);
    $.post('ScheduledRequestHandlerServlet', {"idRequestReference": getJsonRequestValidation().idRequestReference, "operation": "remove"},
    function(resp) { // on success
        alert(resp);
        //elementClear();
        getValidationScenarioList('RequestValidationFilter', getJsonRequestValidation().idRequestReference);
    })
            .fail(function() { //on failure
                alert("Failed during scenario unschedule!");
            });
}
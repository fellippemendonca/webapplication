// When the page is fully loaded...
/* RequestTableData(tagElementId); //Preenche tabela tendo como base a lista de tags pela qual deve utilizar como filtro
 * 
 */

function getIdRequestReference() {
    return idRequestReference;
}
function setIdRequestReference(id) {
    idRequestReference = id;
}

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

function getJsonDailyExecutionRequest() {
    return jsonDailyExecutionRequest;
}
function setJsonDailyExecutionRequest(json) {
    jsonDailyExecutionRequest = json;
}



$(document).ready(function() {


    RequestTableData('tag-array');

    $("#submit-tag-filter").click(function() {
        $("#request-list").empty();
        elementClear();
        RequestTableData('tag-array');
    });

    $('#datepicker-field').datepicker({dateFormat: 'yy-mm-dd', autoclose: true});
    $('#datepicker-field').datepicker('setDate', getDateNow());
    $('#datepicker-field').datepicker("option", "onClose", function(dateText, inst) {
        fireAlert(dateText);
    });
});


/*Função que busca lista de requests e filtra exibição do resultado com base na lista de tags*/
function RequestTableData(tagElementId) {
    var filter = new Array();
    if (document.getElementById(tagElementId).value != "") {
        filter = document.getElementById(tagElementId).value.split(",").map(String);
    }

    $.get('ScheduledRequests', {"tag_array": filter},
    function(resp) {
        var requestList = JSON.parse(resp).requestList;
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
        setIdRequestReference(requestList[row.cells[0].innerHTML].dbId);
        document.getElementById('request-name-indicator').value = requestList[row.cells[0].innerHTML].requestName;
        document.getElementById('request-env-indicator').value = requestList[row.cells[0].innerHTML].environment;
        getRequestLogByDate("RequestDailyExecution", requestList[row.cells[0].innerHTML].dbId, getSelectedDate());

    };

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

/* Recebe o json com a lista que contem jsonScheduledRequestExecutionLogList -> log/date -> jsonRequestValidation  */
function getRequestLogByDate(url, idRequestReference, date) {
    var dataOutput;
    $.get(url, {"requestId": idRequestReference, "executionDate": date}, function(data) {
        pospulateRequestLogByDate(data);
    }, "json")
            .fail(function() { //on failure
                alert("Failed to retrieve information from servlet" + url);
            });
    return dataOutput;
}

function pospulateRequestLogByDate(json) {
    removeInnerElement('daily-execution-log');
    if (json === null) {
        var newdiv1 = document.createElement('a');
        newdiv1.id = 'daily-execution-log' + 'Row';
        newdiv1.className = "list-group-item disabled";
        newdiv1.innerHTML = "Sorry, no executions were found on this date.";
        document.getElementById('daily-execution-log').appendChild(newdiv1);
    } else {
        setJsonDailyExecutionRequest(json);
        var dailyExecutionLogList = new Array();
        dailyExecutionLogList = getJsonDailyExecutionRequest().jsonScheduledRequestExecutionLogList;
        if (dailyExecutionLogList.length > 0) {

            $.each(dailyExecutionLogList, function(i, execution) {
                var executionSuccess = "danger";
                if (execution.success === true) {
                    executionSuccess = "success";
                }

                var newdiv1 = document.createElement('div');
                newdiv1.id = 'daily-execution-log' + 'Row' + i;
                newdiv1.className = "row";

                var cell1 = document.createElement('div');
                cell1.id = "cell1";
                cell1.className = 'col-lg-3';
                var cell2 = document.createElement('div');
                cell2.id = "cell2";
                cell2.className = 'col-lg-9';

                var newdivcollapse = document.createElement('div');
                newdivcollapse.id = 'collapse' + 'Row' + i;
                newdivcollapse.className = "collapse";

                var buttoncollapse = document.createElement('a');
                buttoncollapse.id = 'collapse-button' + 'Row' + i;
                buttoncollapse.className = "list-group-item list-group-item-" + executionSuccess;
                buttoncollapse.setAttribute("onClick", "collapseButton(" + newdivcollapse.id + ")");
                buttoncollapse.innerHTML = execution.executionDate;

                var newdiv2 = document.createElement('div');
                newdiv2.id = 'validation-list-div' + 'Table' + i;
                newdiv2.className = "Table";
                populateRequestValidationScenarioList(newdiv2, execution.jsonRequestValidation, execution.idScheduledRequestExecutionLog);

                newdivcollapse.appendChild(newdiv2);

                cell1.appendChild(buttoncollapse);
                cell2.appendChild(newdivcollapse);

                newdiv1.appendChild(cell1);
                newdiv1.appendChild(cell2);

                document.getElementById('daily-execution-log').appendChild(newdiv1);
                
                var divideRow = document.createElement('hr');
                divideRow.setAttribute("width", "75%");
                divideRow.setAttribute("align", "left");
                document.getElementById('daily-execution-log').appendChild(divideRow);
            });
        } else {
            var newdiv1 = document.createElement('a');
            newdiv1.id = 'daily-execution-log' + 'Row';
            newdiv1.className = "list-group-item disabled";
            newdiv1.innerHTML = "Sorry, no executions were found on this date.";
            document.getElementById('daily-execution-log').appendChild(newdiv1);
        }
    }
}

/*Função que preenche os cenarios de validaçao com base no json fornecido*/
function populateRequestValidationScenarioList(divElem, json, logId) {
    RequestValidationCounter = 0;
    var validationScenarioList = new Array();
    validationScenarioList = json.jsonValidationScenarioList;
    if (validationScenarioList.length > 0) {
        $.each(validationScenarioList, function(i, scenario) {
            var newdiv1 = document.createElement('div');
            newdiv1.id = divElem + 'Row' + i;
            newdiv1.className = "row";
            divElem.appendChild(newdiv1);
            addValidationScenario(newdiv1, scenario.idValidationScenario, scenario.validationScenarioDescription, scenario.success, logId);
        });
    } else {
        //alert("Nenhum cenário cadastrado!");
    }
}
/*------------------PREENCHER DADOS DE VALIDAÇÃO------------------------------*/

function validationEdit(logId, scenarioId) {
    var scenario = findScenarioByLogAndId(logId, scenarioId);
    populateValidationFields(scenario);
    printValidationResponse(scenario);
    $('#myModal').modal('show');
}

function el(id) {
    return document.getElementById(id);
}

function populateValidationFields(scenario) {
    requestClear();
    var jsonValidationScenario = scenario;
    var requestObj = JSON.parse(scenario.requestObject);

    el('request-id').value = jsonValidationScenario.idRequestReference;
    el('requestName').value = requestObj.requestName;
    el('method').value = requestObj.method;
    el('environment').value = requestObj.environment;
    el('scheme').value = requestObj.scheme;
    el('host').value = requestObj.host;
    el('path').value = requestObj.path;
    el('Payload').value = requestObj.payload;

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

}



function isScheduled(requestId) {
    var dataOutput = false;
    $.get("ScheduledRequests", {"requestId": "busca"}, function(data) {
        var requestList = JSON.parse(data).requestList;
        $.each(requestList, function(i, request) {
            if (request.dbId === requestId) {
                dataOutput = true;
            }
        });
    }, "json")
            .fail(function() { //on failure
                alert("Failed to retrieve information from servlet");
            });
    return dataOutput;
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

function collapseButton(fieldId) {
    $("#" + fieldId.id).collapse('toggle');
}

function findScenarioByLogAndId(idScheduledRequestExecutionLog, index) {
    var scenario = null;
    var dailyExecutionLogList = new Array();
    dailyExecutionLogList = getJsonDailyExecutionRequest().jsonScheduledRequestExecutionLogList;
    if (dailyExecutionLogList.length > 0) {
        $.each(dailyExecutionLogList, function(i, execution) {
            if (execution.idScheduledRequestExecutionLog === idScheduledRequestExecutionLog) {
                scenario = execution.jsonRequestValidation.jsonValidationScenarioList[index];
            }
        });
    } else {
        //nada;
    }
    return scenario;
}

/*-----------------------DATE-PICKER-------------------------------------------*/

function fireAlert(date) {
    getRequestLogByDate("RequestDailyExecution", getIdRequestReference, date);
    //alert("alert fired"+date);
    //RequestTableData('tag-array');
}

function getDateNow() {
    var nowTemp = new Date();
    var now = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0);
    return now;
}

function getSelectedDate() {
    return(el('datepicker-field').value);
}

function el(id) {
    return document.getElementById(id);
}

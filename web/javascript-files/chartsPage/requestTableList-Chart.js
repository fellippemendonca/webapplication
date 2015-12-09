// When the page is fully loaded...
/* RequestTableData(tagElementId); //Preenche tabela tendo como base a lista de tags pela qual deve utilizar como filtro
 * 
 */

function getIdQueryReport() {
    return IdQueryReport;
}
function setIdQueryReport(id) {
    IdQueryReport = id;
}

function getJsonQueryReport() {
    return JsonQueryReport;
}
function setJsonQueryReport(json) {
    JsonQueryReport = json;
}



$(document).ready(function() {

    RequestTableData('tag-array');

    $("#submit-tag-filter").click(function() {
        $("#query-list").empty();
        queryClear();
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

    $.get('QueryReport', {"tag_array": filter},
    function(resp) {
        var queryList = resp;
        var tableshow = "<table id='queryTable' class='table table-bordered' cellspacing='0' width='100%'>";
        tableshow += "<thead><tr><th>id</th><th>Database</th><th>Name</th></tr></thead><tbody>";

        $.each(queryList, function(i, request) {
            if (compareLists(filter, getNameList(request))) {
                tableshow += "<tr><td>" + i + "</td><td>" + request.dbName + "</td><td>" + request.name + "</td></tr>";
            }
        });

        tableshow += "</tbody></table>";
        $("#query-list").empty()
                .append("<br>");
        $("#query-list").append(tableshow);

        Synthesizer(queryList);
    })
            .fail(function() { // on failure
                alert("Request failed.");
            });
}
/*----------------------------------------------------------------------------*/

/*--Função que detecta linha da tabela clicada e preenche os campos do request*/
function Synthesizer(list) {
    var queryList = list;
    var table = document.getElementById("queryTable");
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
        setIdQueryReport(queryList[row.cells[0].innerHTML].id);
        setJsonQueryReport(queryList[row.cells[0].innerHTML]);
        removeInnerElement('chart_div'); 
        document.getElementById('database-name-indicator').value = queryList[row.cells[0].innerHTML].dbName;
        document.getElementById('query-name-indicator').value = queryList[row.cells[0].innerHTML].name;
        drawChart(queryList[row.cells[0].innerHTML].id , getSelectedDate());
        document.getElementById('edit-query-button').className = 'btn btn-default';
        
        
        //Trava cursor após preencher o response
        //$("body").toggleClass("wait");
        //Scroll into position
        document.getElementById("filter-view-div").scrollIntoView();
    };

}
/*----------------------------------------------------------------------------*/
google.load("visualization", "1", {packages: ["corechart"]});
//google.setOnLoadCallback(drawChart(requestList[row.cells[0].innerHTML].id , getSelectedDate()));

function drawChart(queryId,since) {
    retrieveChartFromLog(queryId, since)
    .done(function(rawdata) {
        var options = {
            title: getJsonQueryReport().name,
            hAxis: {title: 'Tempo', titleTextStyle: {color: '#333'}},
            vAxis: {minValue: 0}
        };
        var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
        var data = google.visualization.arrayToDataTable(transformMatrix(rawdata));
        chart.draw(data, options);
    });
}


function retrieveChartFromLog(queryId, since) {
    $("body").toggleClass("wait");
    return $.post("QueryChart", {"queryId": queryId, "since": since}, null, 'text');
}

function transformMatrix(data) {
    var jsonObject = JSON.parse(data);
    for (var i = 1; i < jsonObject.length; i++) {
        for (var j = 1; j < jsonObject[i].length; j++) {
            jsonObject[i][j] = parseInt(jsonObject[i][j]);
        }
    }
    //Destrava cursor após preencher o response
    $("body").toggleClass("wait");
    return jsonObject;
}


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




/*------------------PREENCHER DADOS DE VALIDAÇÃO------------------------------*/
function queryCreate() {
    queryClear();
    document.getElementById('update-query-button').className = 'btn btn-warning disabled';
    document.getElementById('delete-query-button').className = 'btn btn-danger disabled';
    $('#myModal').modal('show');
    //document.getElementById("validation-view-div").scrollIntoView();
}

function queryEdit() {
    populateQueryFields(getJsonQueryReport());
    document.getElementById('update-query-button').className = 'btn btn-warning';
    document.getElementById('delete-query-button').className = 'btn btn-danger';
    $('#myModal').modal('show');
}

function el(id) {
    return document.getElementById(id);
}

function populateQueryFields(jsonQueryReport) {
    removeInnerElement('myTableDiv');
    $('#query-tags').tagit('removeAll');
    var queryObj = jsonQueryReport;
    el('query-id').value = queryObj.id;
    el('query-name').value = queryObj.name;
    el('query-description').value = queryObj.description;
    el('databank-name').value = queryObj.dbName;
    el('query-select').value = queryObj.selectQuery;
    /*--------------------------------------------------------------------*/
    var tagList = queryObj.jsonTags;
    $.each(tagList, function(i, tag) {
        $('#query-tags').tagit('createTag', tag.name);
    });

}


/***************************FERRAMENTAS DE TESTE*******************************/
/*function simpleRequest(url, busca) {
    var dataOutput;
    $.get(url, {"requestId": busca}, function(data) {
        printResponse(data);
    }, "json")
            .fail(function() { //on failure
                alert("Failed to retrieve information from servlet" + url);
            });
    return dataOutput;
}*/

/*function printResponse(json) {
    var request = json.jsonValidationScenarioList[1].validationScenarioDescription;
    alert(request.toString());
}*/

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
/*-----------------------DATE-PICKER-------------------------------------------*/

function fireAlert(date) {
    //getRequestLogByDate("RequestDailyExecution", getIdRequestReference, date);
    drawChart(getIdQueryReport() , date);
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




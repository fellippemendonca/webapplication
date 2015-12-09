
$(document).ready(function() {

    /*---------EXECUTA SCENARIO DE VALIDAÇÃO -------------------------------------*/
    
    
    $("#execute-query-button").click(function() {
        var jsonQueryObj = fillQueryObject();
        if (jsonQueryObj === null) {
        } else {
            retrieveFromDatabase();
        }
    });

});


/*--------------------------------CRUD----------------------------------------*/

function insertQueryOnDatabase() {
    var jsonQueryObj = fillQueryObject();
    if (jsonQueryObj === null) {
        //do nothing
    } else {
        $.post('QueryReportCRUDServlet', {"jsonQueryReport": jsonQueryObj, operation: "insert"},
        function(resp) { // on success
            alert(resp);
            queryClear();
            RequestTableData('tag-array');
        })
                .fail(function() { //on failure
                    alert("Failed to insert the new Query!");
                });
    }
}


function updateQueryFromDatabase() {
    var jsonQueryObj = fillQueryObject();
    if (jsonQueryObj === null) {
        //do nothing
    } else {
        $.post('QueryReportCRUDServlet', {"jsonQueryReport": jsonQueryObj, operation: "update"},
        function(resp) { // on success
            alert(resp);
            queryClear();
            RequestTableData('tag-array');
        })
                .fail(function() { //on failure
                    alert("Failed to update the selected request!");
                });
    }
}

function deleteQueryFromDatabase() {
    var jsonQueryObj = fillQueryObject();
    if (jsonQueryObj === null) {
        //do nothing
    } else {
        $.post('QueryReportCRUDServlet', {"jsonQueryReport": jsonQueryObj, operation: "remove"},
        function(resp) { // on success
            alert(resp);
            queryClear();
            RequestTableData('tag-array');
        })
                .fail(function() { //on failure
                    alert("Failed to delete the selected request!");
                });
    }
}
/*----------------------------------------------------------------------------*/

function fillQueryObject() {
    var Id = $("#query-id").val();
    if ($("#query-id").val() === "") {
        Id = "0";
    } else {
        Id = $("#query-id").val();
    }

    var Name = $("#query-name").val();
    var Description = $("#query-description").val();
    var DbName = $("#databank-name").val();
    var SelectQuery = $("#query-select").val();
 

    //-----Arrays
    var Tags = new Array();
    populateTagArray(Tags, "query-tags");

    if (Name === "" || Description === "" || DbName === "" || SelectQuery === "") {
        alert("Dados insuficientes para realizar o request!");
        return null;
    } else {
        var QueryReportObj = {
            id: Id
            , name: Name
            , description: Description
            , dbName: DbName
            , selectQuery: SelectQuery
            , jsonTags: Tags};
        var queryReportObj = JSON.stringify(QueryReportObj);
        return queryReportObj;
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
/*----------------------------------------------------------------------------*/

function retrieveFromDatabase() {
    //Trava cursor após preencher o response
    $("body").toggleClass("wait");
    $.post("QueryRetrieve", {"database": $("#databank-name").val(), "select": $("#query-select").val()},
    function(data, status) {
        CreateTable("myTableDiv", data);
    }, 'text');
}


function CreateTable(tableName, returnedJson) {
    document.getElementById(tableName).innerHTML = "";
    var tableDiv = document.getElementById(tableName);
    var jsonObject = JSON.parse(returnedJson);

    var table = document.createElement('TABLE');
    table.border = '1';
    var tableHead = document.createElement('THEAD');
    var tableBody = document.createElement('TBODY');
    table.appendChild(tableHead);
    table.appendChild(tableBody);

    for (var i = 0; i < jsonObject.colName.column.length; i++) {
        var th = document.createElement('TH');
        th.width = '75';
        th.appendChild(document.createTextNode(jsonObject.colName.column[i].value));
        tableHead.appendChild(th);
    }

    for (var i = 0; i < jsonObject.rows.length; i++) {
        var tr = document.createElement('TR');
        tableBody.appendChild(tr);
        var columns = jsonObject.rows[i].column;

        for (var j = 0; j < columns.length; j++) {
            var td = document.createElement('TD');
            td.width = '75';
            td.appendChild(document.createTextNode(columns[j].value));
            tr.appendChild(td);
        }
    }
    //desTrava cursor após preencher o response
    $("body").toggleClass("wait");
    tableDiv.appendChild(table);
}

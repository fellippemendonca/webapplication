// When the page is fully loaded...
/* RequestTableData(tagElementId); //Preenche tabela tendo como base a lista de tags pela qual deve utilizar como filtro
 * 
 */

$(document).ready(function() {
    RequestTableData('tag-array');
   
    $("#submit-tag-filter").click(function() {
        $("#request-list").empty();
        elementClear();
        RequestTableData('tag-array');
    });

});

function RequestTableData(tagElementId) {
    var filter = new Array();
    if (document.getElementById(tagElementId).value != "") {
        filter = document.getElementById(tagElementId).value.split(",").map(String);
    }

    $.get('requestfilter', {"tag_array": filter},
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
        populateFields(row);
    };

    function el(id) {
        return document.getElementById(id);
    }


    function populateFields(row) {
        elementClear();
        el('request-id').value = requestList[row.cells[0].innerHTML].dbId;
        el('requestName').value = requestList[row.cells[0].innerHTML].requestName;
        el('method').value = requestList[row.cells[0].innerHTML].method;
        el('environment').value = requestList[row.cells[0].innerHTML].environment;
        el('scheme').value = requestList[row.cells[0].innerHTML].scheme;
        el('host').value = requestList[row.cells[0].innerHTML].host;
        el('path').value = requestList[row.cells[0].innerHTML].path;
        el('Payload').value = requestList[row.cells[0].innerHTML].payload;

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
        document.getElementById("filter-view-div").scrollIntoView();
    }
}

//------------------------------------------------------------------------------
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


function getIdList(request) {
    var idList = new Array();
    $.each(request.jsonTags, function(i, tag) {
        idList.push(tag.id);
    });
    return idList;
}

function getNameList(request) {
    var idList = new Array();
    $.each(request.jsonTags, function(i, tag) {
        idList.push(tag.name);
    });
    return idList;
}

function simpleRequest(url, busca) {
    var dataOutput;
    $.get(url, {"term": busca}, function(data) {
        dataOutput = data;
    }, "json")
            .fail(function() { //on failure
                alert("Request failed." + url);
            });
    return dataOutput;
}
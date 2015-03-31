// When the page is fully loaded...
$(document).ready(function() {
    var buttonID = "nada";
    $.get('requestfilter', {"button-id": buttonID},
    function(resp) {
        var requestList = JSON.parse(resp).requestList;
        var tableshow = "<table id='requestTable' class='table table-bordered' cellspacing='0' width='100%'>";
        tableshow += "<thead><tr><th>id</th><th>Method</th><th>Environment</th><th>Scheme</th><th>Host</th><th>Path</th></tr></thead><tbody>";

        $.each(requestList, function(i, request) {
            tableshow += "<tr><td>" + i + "</td><td>" + request.method + "</td><td>" + request.environment + "</td><td>" + request.scheme + "</td><td>" + request.host + "</td><td>" + request.path + "</td></tr>";
            i++;
        });

        tableshow += "</tbody></table>";
        $("#request-list").empty()
                .append("<br>");
        $("#request-list").append(tableshow);


        test(requestList);
    })
            .fail(function() { // on failure
                alert("Request failed.");
            });

});

function test(list) {
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
        /*var updateButton = "<input type='submit' class='btn btn-primary' id='submit-request-info' value='Execute'>"
                +" <input type='submit' class='btn btn-success' id='insert-request-info' value='Create'>"
                +" <input type='submit' class='btn btn-warning' id='update-request-info' value='Update'>";

        $('#update-button-div').append(updateButton);*/
        
        el('request-id').value = requestList[row.cells[0].innerHTML].dbId;
        el('method').value = requestList[row.cells[0].innerHTML].method;
        el('environment').value = requestList[row.cells[0].innerHTML].environment;
        el('scheme').value = requestList[row.cells[0].innerHTML].scheme;
        el('host').value = requestList[row.cells[0].innerHTML].host;
        el('path').value = requestList[row.cells[0].innerHTML].path;

        document.getElementById('Template').innerHTML = "";
        var templateList = requestList[row.cells[0].innerHTML].templates;
        $.each(templateList, function(i, template) {
            addElement('Template', template);
        });

        document.getElementById('Header').innerHTML = "";
        var headerList = requestList[row.cells[0].innerHTML].headers;
        $.each(headerList, function(i, header) {
            addElement2('Header', header.name, header.value);
        });

        document.getElementById('Parameter').innerHTML = "";
        var parameterList = requestList[row.cells[0].innerHTML].parameters;
        $.each(parameterList, function(i, parameter) {
            addElement2('Parameter', parameter.name, parameter.value);
        });
    }
}
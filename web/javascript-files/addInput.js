
var ElementCounter = 0;
var ElementLimit = 10;
//Adiciona dinamicamente 1 campo para preencher valores.
function addElement(divName, fieldValue) {
    if (ElementCounter == ElementLimit) {
        alert("You have reached the limit of adding " + ElementCounter + " " + divName);
    }
    else {
        var newdiv = document.createElement('div');
        newdiv.innerHTML = "<input type='text' id='" + divName + "Name" + ElementCounter + "' name='" + divName + "Name' value ='" + fieldValue + "' autocomplete='on'>";
        document.getElementById(divName).appendChild(newdiv);
        $("#" + divName + "Name" + ElementCounter + "").autocomplete(searchParameters(divName + "Name"), "").focus(function() {
            $(this).autocomplete('search', $(this).val());
        });
        ElementCounter++;
    }
}

var ElementCounter2 = 0;
var ElementLimit2 = 10;
//Adiciona dinamicamente 2 campos para preencher valores.
function addElement2(divName, fieldValue1, fieldValue2) {
    if (ElementCounter2 == ElementLimit2) {
        alert("You have reached the limit of adding " + ElementCounter2 + " " + divName);
    } else {
        var newdiv = document.createElement('div');
        newdiv.innerHTML = "<input type='text' id='" + divName + "Name" + ElementCounter2 + "' name='" + divName + "Name' value ='" + fieldValue1 + "' autocomplete='on'>=<input type='text' id='" + divName + "Value" + ElementCounter2 + "' name='" + divName + "Value' value ='" + fieldValue2 + "' autocomplete='on'>";
        document.getElementById(divName).appendChild(newdiv);
        $("#" + divName + "Name" + ElementCounter2 + "").autocomplete(searchParameters(divName + "Name"), "").focus(function() {
            $(this).autocomplete('search', $(this).val());
        });
        $("#" + divName + "Value" + ElementCounter2 + "").autocomplete(searchParameters(divName + "Value"), "").focus(function() {
            $(this).autocomplete('search', $(this).val());
        });
        ElementCounter2++;
    }
}

function removeLastElement(divName) {
    document.getElementById(divName).innerHTML = "";
}

function elementClear() {
    //RequestTableData('tag-array');
    ElementCounter = 0;
    ElementCounter2 = 0;
    document.getElementById("response-div").innerHTML = "";
    $('#request-tags').tagit('removeAll');
    
    document.getElementById('request-id').value = "";
    document.getElementById('method').value = "";
    document.getElementById('environment').value = "";
    document.getElementById('scheme').value = "";
    document.getElementById('host').value = "";
    document.getElementById('path').value = "";
    document.getElementById('Payload').value = "";

    document.getElementById('Template').innerHTML = "";
    document.getElementById('Header').innerHTML = "";
    document.getElementById('Parameter').innerHTML = "";
}

//função para buscar valores dos campos dinamicos no servlet
function searchParameters(parameter) {
    var autocomp_opt = {source: function(request, response)
        {
            $.ajax({
                url: "ajaxautocompleteservlet",
                type: "GET",
                data: {term: request.term, field: parameter},
                dataType: "json",
                success: function(data) {
                    response(data);
                }
            });
        }, minLength: 0
    };
    return autocomp_opt;
}


//Busca valores dos campos fixos no documento.
$(document).ready(function() {
    document.getElementById("navbar").focus();
    
    $("#method").autocomplete(searchParameters("method"), "").focus(function() {
        $(this).autocomplete('search', $(this).val());
    });
    $("#environment").autocomplete(searchParameters("environment"), "").focus(function() {
        $(this).autocomplete('search', $(this).val());
    });
    $("#scheme").autocomplete(searchParameters("scheme"), "").focus(function() {
        $(this).autocomplete('search', $(this).val());
    });
    $("#host").autocomplete(searchParameters("host"), "").focus(function() {
        $(this).autocomplete('search', $(this).val());
    });
    $("#path").autocomplete(searchParameters("path"), "").focus(function() {
        $(this).autocomplete('search', $(this).val());
    });

    $("#tag-array").tagit({
        autocomplete: searchParameters("tag"),
        triggerKeys: ['enter', 'comma', 'tab'],
        sortable: true,
        allowSpaces: true,
        showAutocompleteOnFocus: true
    });

    $("#request-tags").tagit({
        autocomplete: searchParameters("tag"),
        triggerKeys: ['enter', 'comma', 'tab'],
        sortable: true,
        allowSpaces: true//,
        //showAutocompleteOnFocus: false
    });

});


/*  Funções facilitadoras disponíveis para utilização nas Páginas
 *  addElement(divName, fieldName);    //Adiciona um campo com para o nome do Div e o valor predeterminado 
 *  addElement2(divName, fieldValue1, fieldValue2); //Adiciona um campo para nome do Div com nome do campo e valor predeterminado 
 *  // Metodo autocomplete. Adicionado automaticamente para os campos gerados no addElement e manualmente para os demais campos
 */



var ElementCounter = 0;
var ElementLimit = 40;
//Adiciona dinamicamente 1 campo para preencher valores.
function addElement(divName, fieldValue) {
    if (ElementCounter === ElementLimit) {
        alert("You have reached the limit of adding " + ElementCounter + " " + divName);
    } else {
        var tableRow = document.createElement('div');
        tableRow.id = "RowElement" + divName + ElementCounter;
        tableRow.className = "row";

        var cell1 = document.createElement('div');
        cell1.id = "cell1";
        cell1.className = 'col-lg-10';

        var cell3 = document.createElement('div');
        cell3.id = "cell3";
        cell3.className = 'col-lg-1';

        /*-------------------------------------------*/
        var deleteButton = document.createElement('button');
        deleteButton.id = "removeButton" + ElementCounter;
        deleteButton.className = 'btn btn-danger btn-xs';
        deleteButton.innerHTML = "-";
        deleteButton.setAttribute("onClick", "removeElement('" + tableRow.id + "');");
        /*-------------------------------------------*/

        var newdiv = document.createElement('input');
        newdiv.id = divName + "Name" + ElementCounter;
        newdiv.setAttribute("type", "text");
        newdiv.name = divName + "Name";
        newdiv.className = "form-control";
        newdiv.value = fieldValue;

        cell1.appendChild(newdiv);
        cell3.appendChild(deleteButton);
        tableRow.appendChild(cell1);
        tableRow.appendChild(cell3);

        document.getElementById(divName).appendChild(tableRow);

        $("#" + newdiv.id).autocomplete(searchParameters(newdiv.name), "").focus(function() {
            $(this).autocomplete(searchConditionalParameters(newdiv.name,$("#path").val()));
            $(this).autocomplete('search', $(this).val());
        });

        ElementCounter++;
    }
}


var ElementCounter2 = 0;
var ElementLimit2 = 40;
//Adiciona dinamicamente 2 campos para preencher valores.
function addElement2(divName, fieldValue1, fieldValue2) {
    if (ElementCounter2 === ElementLimit2) {
        alert("You have reached the limit of adding " + ElementCounter2 + " " + divName);
    } else {

        var tableRow = document.createElement('div');
        tableRow.id = "RowElement" + divName + ElementCounter2;
        tableRow.className = "row";

        var cell1 = document.createElement('div');
        cell1.id = "cell1";
        cell1.className = 'col-lg-5';

        var cell2 = document.createElement('div');
        cell2.id = "cell2";
        cell2.className = 'col-lg-5';

        var cell3 = document.createElement('div');
        cell3.id = "cell3";
        cell3.className = 'col-lg-1';

        /*-------------------------------------------*/
        var deleteButton = document.createElement('button');
        deleteButton.id = "removeButton" + ElementCounter2;
        deleteButton.className = 'btn btn-danger btn-xs';
        deleteButton.innerHTML = "-";
        deleteButton.setAttribute("onClick", "removeElement('" + tableRow.id + "');");
        /*-------------------------------------------*/

        var newdiv = document.createElement('input');
        newdiv.id = divName + "Name" + ElementCounter2;
        newdiv.setAttribute("type", "text");
        newdiv.name = divName + "Name";
        newdiv.className = "form-control";
        newdiv.value = fieldValue1;

        var newdiv2 = document.createElement('input');
        newdiv2.id = divName + "Value" + ElementCounter2;
        newdiv2.setAttribute("type", "text");
        newdiv2.name = divName + "Value";
        newdiv2.className = "form-control";
        newdiv2.value = fieldValue2;

        cell1.appendChild(newdiv);
        cell2.appendChild(newdiv2);
        cell3.appendChild(deleteButton);
        tableRow.appendChild(cell1);
        tableRow.appendChild(cell2);
        tableRow.appendChild(cell3);

        document.getElementById(divName).appendChild(tableRow);

        $("#" + newdiv.id).autocomplete(searchParameters(newdiv.name), "").focus(function() {
            $(this).autocomplete(searchConditionalParameters(newdiv.name, $("#host").val()));
            $(this).autocomplete('search', $(this).val());
        });
        $("#" + newdiv2.id).autocomplete(searchParameters(newdiv2.name), "").focus(function() {
            $(this).autocomplete(searchConditionalParameters(newdiv2.name, newdiv.value));
            $(this).autocomplete('search', $(this).val());
        });



        ElementCounter2++;
    }
}

function removeLastElement(divName) {
    document.getElementById(divName).innerHTML = "";
}

function removeInnerElement(divName) {
    document.getElementById(divName).innerHTML = "";
}
function removeElementValue(divName) {
    document.getElementById(divName).value = "";
}
function setElementValue(divName, content) {
    document.getElementById(divName).value = content;
}

function removeDivElement(divName) {
    $("#" + divName).remove();
    $("#" + divName + "remove").remove();
    //document.getElementById(divName).remove();
}
function removeElement(elementId) {
    if (document.getElementById(elementId) == null) {
        //do Nothing
    } else {
        var element = document.getElementById(elementId);
        element.parentNode.removeChild(element);
    }
}

//Função que limpa os campos que estavam preenchidos
function elementClear() {
    ElementCounter = 0;
    ElementCounter2 = 0;
    document.getElementById("response-div").innerHTML = "";
    $('#request-tags').tagit('removeAll');
    removeElementValue('requestName');
    setElementValue('requestDescription',"Nenhuma descricao foi definida para este request.");
    removeElementValue('request-id');
    removeElementValue('method');
    removeElementValue('environment');
    removeElementValue('scheme');
    removeElementValue('host');
    removeElementValue('path');
    removeElementValue('Payload');
    /*Dynamic data*/
    removeElementValue('databank-selector');
    removeElementValue('dynamic-data-query');
    /*------------*/
    removeInnerElement('Template');
    removeInnerElement('Header');
    removeInnerElement('Parameter');
}

//Função que busca valores no servlet com base no nome do campo.
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

//Função que busca valores no servlet com base no nome do campo + criterias.
function searchConditionalParameters(parameter, criteria) {
    var autocomp_opt = {source: function(request, response)
        {
            $.ajax({
                url: "AjaxConditionalAutocompleteServlet",
                type: "GET",
                data: {term: request.term, field: parameter, criteria: criteria},
                dataType: "json",
                success: function(data) {
                    response(data);
                }
            });
        }, minLength: 0
    };
    return autocomp_opt;
}


//Aplica função Autocomplete nos campos do documento.
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
        $(this).autocomplete(searchConditionalParameters("host", $("#environment").val()));
        $(this).autocomplete('search', $(this).val());
    });
    $("#path").autocomplete(searchParameters("path"), "").focus(function() {
        $(this).autocomplete(searchConditionalParameters("path", $("#host").val()));
        $(this).autocomplete('search', $(this).val());
        $(this).autocomplete("option", "appendTo", $(this).id);
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
        allowSpaces: true
    });





});

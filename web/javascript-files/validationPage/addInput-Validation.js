
/*  Funções facilitadoras disponíveis para utilização nas Páginas
 *  addElement(divName, fieldName);    //Adiciona um campo com para o nome do Div e o valor predeterminado 
 *  addElement2(divName, fieldValue1, fieldValue2); //Adiciona um campo para nome do Div com nome do campo e valor predeterminado 
 *  // Metodo autocomplete. Adicionado automaticamente para os campos gerados no addElement e manualmente para os demais campos
 */



var ElementCounter = 0;
var ElementLimit = 10;
//Adiciona dinamicamente 1 campo para preencher valores.
function addElement(divName, fieldValue) {
    if (ElementCounter === ElementLimit) {
        alert("You have reached the limit of adding " + ElementCounter + " " + divName);
    } else {
        var tableRow = document.createElement('div');
        tableRow.id = "RowElement" + ElementCounter;
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
            $(this).autocomplete('search', $(this).val());
            $(this).autocomplete( "option", "appendTo", $(this).id );
        });
        ElementCounter++;
    }
}


var ElementCounter2 = 0;
var ElementLimit2 = 10;
//Adiciona dinamicamente 2 campos para preencher valores.
function addElement2(divName, fieldValue1, fieldValue2) {
    if (ElementCounter2 === ElementLimit2) {
        alert("You have reached the limit of adding " + ElementCounter2 + " " + divName);
    } else {
        
        var tableRow = document.createElement('div');
        tableRow.id = "RowElement" + ElementCounter2;
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
            $(this).autocomplete('search', $(this).val());
        });
        
        $("#" + newdiv2.id).autocomplete(searchParameters(newdiv2.name), "").focus(function() {
            $(this).autocomplete('search', $(this).val());
        });
        ElementCounter2++;
    }
}

var ElementCounter3 = 0;
var ElementLimit3 = 40;
function addValidationElement(divName, optionValue, fieldValue, optionName, ledColor) {
    if (ElementCounter3 == ElementLimit3) {
        alert("You have reached the limit of adding " + ElementCounter3 + " " + divName);
    } else {

        var validationElementLabel = document.createElement("Label");
        validationElementLabel.id = "Label" + ElementCounter3;
        validationElementLabel.setAttribute("for", "recipient-name");
        validationElementLabel.innerHTML = optionName;

        var tableRow = document.createElement('div');
        tableRow.id = "RowElement" + ElementCounter3;
        tableRow.className = "row";

        var cell1 = document.createElement('div');
        cell1.id = "cell1";
        cell1.className = 'col-lg-8';
        var cell2 = document.createElement('div');
        cell2.id = "cell2";
        cell2.className = 'col-lg-1';
        var cell3 = document.createElement('div');
        cell3.id = "cell3";
        cell3.className = 'col-lg-1';

        /*-------------------------------------------*/
        var deleteButton = document.createElement('button');
        deleteButton.id = "removeButton" + ElementCounter3;
        deleteButton.className = 'btn btn-danger btn-xs';
        deleteButton.innerHTML = "-";
        deleteButton.setAttribute("onClick", "removeElement('" + tableRow.id + "'); removeElement('" + validationElementLabel.id + "');");
        /*-------------------------------------------*/

        var ledIndicator = document.createElement('div');
        ledIndicator.className = 'led-' + ledColor + ''; //style="float: left;"

        var newdiv = document.createElement('div');
        newdiv.id = divName + "Element" + ElementCounter3;
        newdiv.setAttribute("name", divName + "Element");
        newdiv.name = divName + "Element";
        newdiv.title = optionValue;
        newdiv.className = "form-group";



        switch (optionValue) {
            case "Find Http Status":
                newdiv.innerHTML += "<input type='text' class='form-control' id='" + divName + "Value" + ElementCounter3 + "' name='" + divName + "Value' value ='" + fieldValue + "'></input>";
                break;
            case "Response Time":
                newdiv.innerHTML += "<input type='text' class='form-control' id='" + divName + "Value" + ElementCounter3 + "' name='" + divName + "Value' value ='" + fieldValue + "'></input>";
                break;
            case "Find String":
                newdiv.innerHTML += "<input type='text' class='form-control' id='" + divName + "Value" + ElementCounter3 + "' name='" + divName + "Value' value ='" + fieldValue + "'></input>";
                break;
            case "Find Json Element":
                newdiv.innerHTML += "<input type='text' class='form-control' id='" + divName + "Value" + ElementCounter3 + "' name='" + divName + "Value' value ='" + fieldValue + "'></input>";
                break;
            case "Compare Json Structure":
                newdiv.innerHTML += "<textarea class='form-control' id='" + divName + "Value" + ElementCounter3 + "' name='" + divName + "Value'>" + fieldValue + "</textarea>";
                break;
            default:
        }

        cell1.appendChild(newdiv);
        cell2.appendChild(ledIndicator);
        cell3.appendChild(deleteButton);
        tableRow.appendChild(cell1);
        tableRow.appendChild(cell2);
        tableRow.appendChild(cell3);


        document.getElementById(divName).appendChild(validationElementLabel);
        document.getElementById(divName).appendChild(tableRow);
        ElementCounter3++;
    }
}



//Inclui Cenário do request.
var RequestValidationCounter = 0;
var RequestValidationCounterLimit = 10;
function addValidationScenario(divName, idValidationScenario, validationScenarioDescription, success) {
    if (RequestValidationCounter == RequestValidationCounterLimit) {
        alert("You have reached the limit of adding " + RequestValidationCounter + " " + divName);
    } else {
        var cell1 = document.createElement('div');
        cell1.id = "cell1";
        cell1.className = 'col-lg-6';
        var cell2 = document.createElement('div');
        cell2.id = "cell2";
        cell2.className = 'col-lg-6';

        var newdiv2 = document.createElement('div');
        newdiv2.id = RequestValidationCounter;
        if (success === true) {
            newdiv2.className = 'list-group-item list-group-item-success';
        } else {
            newdiv2.className = 'list-group-item list-group-item-danger';
        }
        newdiv2.innerHTML = "" + validationScenarioDescription + "";
        /*-------------------------------------------*/
        var newdiv3 = document.createElement('button');
        newdiv3.id = idValidationScenario;
        newdiv3.className = 'btn btn-primary';
        newdiv3.innerHTML = "Edit";
        newdiv3.setAttribute("onClick", "validationEdit(" + RequestValidationCounter + ")");
        /*-------------------------------------------*/
        cell1.appendChild(newdiv2);
        cell2.appendChild(newdiv3);
        document.getElementById(divName).appendChild(cell1);
        document.getElementById(divName).appendChild(cell2);
        RequestValidationCounter++;
    }
}

function removeInnerElement(divName) {
    document.getElementById(divName).innerHTML = "";
}
function removeElementValue(divName) {
    document.getElementById(divName).value = "";
}
function removeElement(elementId) {
    if (document.getElementById(elementId) == null) {
        //do Nothing
    } else {
        var element = document.getElementById(elementId);
        element.parentNode.removeChild(element);
    }
}

function removeDivElement(divName) {
    $("#" + divName).remove();
    $("#" + divName + "remove").remove();
    //document.getElementById(divName).remove();
}

//Função que limpa os campos que estavam preenchidos
function elementClear() {
    ElementCounter = 0;
    ElementCounter2 = 0;
    ElementCounter3 = 0;
    document.getElementById('create-new-validation-button').className = 'btn btn-primary btn-lg disabled';
    document.getElementById('execute-massive-validation-button').className = 'btn btn-primary disabled';
    removeElement("scheduleButton");
    removeInnerElement('validation-list-div');
    document.getElementById('schedule-button').className = 'btn btn-default dropdown-toggle disabled';
    $('#request-tags').tagit('removeAll');
    removeElementValue('request-name-indicator');
    removeElementValue('request-env-indicator');
    removeElementValue('request-id');
    removeElementValue('method');
    removeElementValue('environment');
    removeElementValue('scheme');
    removeElementValue('host');
    removeElementValue('path');
    removeElementValue('Payload');
    removeInnerElement('response-scenario-div');
    removeInnerElement('Template');
    removeInnerElement('Header');
    removeInnerElement('Parameter');
    removeElementValue('scenario-description');
    removeInnerElement('Validation');
}

function requestClear() {
    ElementCounter = 0;
    ElementCounter2 = 0;
    ElementCounter3 = 0;
    //document.getElementById('create-new-validation-button').className = 'btn btn-primary btn-lg disabled';
    //document.getElementById('execute-massive-validation-button').className = 'btn btn-primary disabled';
    //removeElement("scheduleButton");
    //removeInnerElement('validation-list-div');
    //document.getElementById('schedule-button').className = 'btn btn-default dropdown-toggle disabled';
    $('#request-tags').tagit('removeAll');
    removeElementValue('request-id');
    removeElementValue('method');
    removeElementValue('environment');
    removeElementValue('scheme');
    removeElementValue('host');
    removeElementValue('path');
    removeElementValue('Payload');
    removeInnerElement('response-scenario-div');
    removeInnerElement('Template');
    removeInnerElement('Header');
    removeInnerElement('Parameter');
    removeElementValue('scenario-description');
    removeInnerElement('Validation');
}

function ValidationClear() {
    ElementCounter3 = 0;
    removeElementValue('scenario-description');
    removeInnerElement('Validation');
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
        allowSpaces: true
    });
});

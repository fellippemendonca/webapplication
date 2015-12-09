

function removeInnerElement(divName) {
    document.getElementById(divName).innerHTML = "";
}
function removeElementValue(divName) {
    document.getElementById(divName).value = "";
}
function removeElement(elementId) {
    var element = document.getElementById(elementId);
    element.parentNode.removeChild(element);
}
function removeDivElement(divName) {
    $("#" + divName).remove();
    $("#" + divName + "remove").remove();
    //document.getElementById(divName).remove();
}

//Função que limpa os campos que estavam preenchidos
function elementClear() {
    $('#query-tags').tagit('removeAll');
    removeElementValue('query-id');
    removeElementValue('query-name');
    removeElementValue('query-description');
    removeElementValue('query-select');
    removeInnerElement('myTableDiv');
    removeElementValue('database-name-indicator');
    removeElementValue('query-name-indicator');
    removeInnerElement('chart_div');   
}

function queryClear() {
    document.getElementById('edit-query-button').className = 'btn btn-default disabled';
    $('#query-tags').tagit('removeAll');
    removeElementValue('query-id');
    removeElementValue('query-name');
    removeElementValue('query-description');
    removeElementValue('query-select');
    removeInnerElement('myTableDiv');
    removeElementValue('database-name-indicator');
    removeElementValue('query-name-indicator');
    removeInnerElement('chart_div'); 
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
    
    $("#tag-array").tagit({
        autocomplete: searchParameters("query-tag"),
        triggerKeys: ['enter', 'comma', 'tab'],
        sortable: true,
        allowSpaces: true,
        showAutocompleteOnFocus: true
    });

    $("#query-tags").tagit({
        autocomplete: searchParameters("query-tag"),
        triggerKeys: ['enter', 'comma', 'tab'],
        sortable: true,
        allowSpaces: true//,
        //showAutocompleteOnFocus: true
    });
});


var ElementCounter = 0;
var ElementLimit = 10;
function addElement(divName) {

    if (ElementCounter == ElementLimit) {
        alert("You have reached the limit of adding " + ElementCounter + " " + divName);
    }
    else {
        var newdiv = document.createElement('div');
        newdiv.innerHTML = "<input type='text' id='" + divName + "Name" + ElementCounter + "' name='" + divName + "Name' autocomplete='on'>";
        document.getElementById(divName).appendChild(newdiv);
        $("#" + divName + "Name" + ElementCounter + "").autocomplete(searchParameters(divName + "Name"), "");
        ElementCounter++;
    }
}

var ElementCounter2 = 0;
var ElementLimit2 = 10;
function addElement2(divName) {


    if (ElementCounter2 == ElementLimit2) {
        alert("You have reached the limit of adding " + ElementCounter2 + " " + divName);
    } else {
        var newdiv = document.createElement('div');
        newdiv.innerHTML = "<input type='text' id='" + divName + "Name" + ElementCounter2 + "' name='" + divName + "Name' autocomplete='on'>=<input type='text' id='" + divName + "Value" + ElementCounter2 + "' name='" + divName + "Value' autocomplete='on'>";
        document.getElementById(divName).appendChild(newdiv);
        $("#" + divName + "Name" + ElementCounter2 + "").autocomplete(searchParameters(divName + "Name"), "");
        $("#" + divName + "Value" + ElementCounter2 + "").autocomplete(searchParameters(divName + "Value"), "");
        ElementCounter2++;
    }
}

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
        }
    };
    return autocomp_opt;
}
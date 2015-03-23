$(document).ready(function() {

    $("#submit-request-info").click(function() {
        var Environment = $("#environment").val();
        var Method = $("#method").val();
    	var Scheme = $("#scheme").val();
        var Host = $("#host").val();
        var Path = $("#path").val();

        //-----Arrays
        var Headers = new Array();
        populateDualArray(Headers, "HeaderName", "HeaderValue");
        
        var Templates = new Array();
        populateArray(Templates, "TemplateName");
        
        var Texugos = new Array();
        populateArray(Texugos, "TexugoName");

        var Parameters = new Array();
        populateDualArray(Parameters, "ParameterName", "ParameterValue");        
        //-----
        
    	if (Environment === "" || Method === "" || Scheme === "" || Host === "" || Path === "") {
    		alert("Not enough information for an insertion!");
    		return;
    	}
        
        var RequestObj = {environment:Environment
           ,method:Method
           ,scheme:Scheme
           ,host:Host
           ,path:Path
           ,templates:Templates
           ,texugos:Texugos
           ,headers:Headers
           ,parameters:Parameters};
        
        var jsonRequestObj = JSON.stringify(RequestObj);                    

    	$.post('restexecutorservlet',{"jsonRequestObj":jsonRequestObj},
            function(resp) { // on success
                printResponse(resp);
                alert("Insertion successful!"+resp);
            })
            .fail(function() { //on failure
                alert("Insertion failed.");
            });
    });
});

function populateArray(arrayToFill, input){
        var arr = new Array();
        arr = document.getElementsByName(input);
        for(var i = 0; i < arr.length; i++)
        {
            var obj = document.getElementsByName(input).item(i);
            arrayToFill.push(obj.value);
        }
}

function populateDualArray(arrayToFill, input1, input2){
        var arr1 = new Array();
        arr1 = document.getElementsByName(input1);
        for(var i = 0; i < arr1.length; i++)
        {
            var obj1 = document.getElementsByName(input1).item(i);
            var obj2 = document.getElementsByName(input2).item(i);
            var value1 = obj1.value;
            var value2 = obj2.value;
            var h = {name:value1, value:value2};
            arrayToFill.push(h);
        }
}

function printResponse(json) {
        var data = json;
    
	// First empty the <div> completely and add a title.
	$("#response-div").empty()
		.append("<h4>Response:</h4>");
        $("#response-div").append(data);
        //$("#request-list").append();
		
	// Then add every band name contained in the list.	
	//$.each(json, function(i, name) {
		//$("#request-list").append(i + 1, ". " + name + " </br>");
	//});
};
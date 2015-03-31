// Both those functions get a json object as an argument,
// which itself also holds other objects.

// 1. The first function is supposed to get an object 
// 	containing just a list of band names.
// 2. The second function is supposed to get an object containing
// 	bands with albums, which essentially means a list of objects
// 	which hold (1) a band name and (2) a list of albums.

function printBands(jsonString) {
        var json = JSON.parse(jsonString);
        
        var tableshow = "<tr><th>Host</th><th>Method</th></tr>";
            tableshow += "<tr><td>"+json.requestList[0].host+"</td><td>"+json.requestList[0].method+"</td></tr>";
            tableshow += "<tr><td>"+json.requestList[1].host+"</td><td>"+json.requestList[1].method+"</td></tr>";

        $("#request-list").empty()
		.append("<br>");
        $("#request-list").append(tableshow);
        //$("#request-list").append();
		
	// Then add every band name contained in the list.	
	//$.each(json, function(i, name) {
		//$("#request-list").append(i + 1, ". " + name + " </br>");
	//});
};

function printBandsAndAlbums(json) {
	
	// First empty the <div> completely and add a title.
	$("#bands-albums-results").empty()
		.append("<h3>Band Names and Albums</h3>");
	
	// Get each band object...
	$.each(json, function(i, bandObject) {
		
		// Add to the <div> every band name...
		$("#bands-albums-results").append(i + 1, ". " + bandObject.bandName + " </br>");
		// And then for every band add a list of their albums.
		$.each(bandObject.bandAlbums, function(i, album) {
			$("#bands-albums-results").append("--" + album + "</br>");
		});
	});
};

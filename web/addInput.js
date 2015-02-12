var counter = 0;
var limit = 3;
function addHeader(divName){
     if (counter == limit)  {
          alert("You have reached the limit of adding " + counter + " Headers");
     }
     else {
          var newdiv = document.createElement('div');
          newdiv.innerHTML = "Header " + (counter + 1) + "<input type='text' name='HeaderNames[]'>=<input type='text' name='HeaderValues[]'></td></tr>";
          document.getElementById(divName).appendChild(newdiv);
          counter++;
     }
}

var ParameterCounter = 0;
var ParameterLimit = 3;
function addParameter(divName){
     if (ParameterCounter == ParameterLimit)  {
          alert("You have reached the limit of adding " + ParameterCounter + " Parameters");
     }
     else {
          var newdiv = document.createElement('div');
          newdiv.innerHTML = "Parameter " + (ParameterCounter + 1) + "<input type='text' name='ParameterNames[]'>=<input type='text' name='ParameterValues[]'></td></tr>";
          document.getElementById(divName).appendChild(newdiv);
          ParameterCounter++;
     }
}
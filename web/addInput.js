var counter = 0;
var limit = 3;
function addHeader(divName){
     if (counter == limit)  {
          alert("You have reached the limit of adding " + counter + " Headers");
     }
     else {
          var newdiv = document.createElement('div');
          newdiv.innerHTML = (counter + 1) + "- <input type='text' name='HeaderNames[]'>=<input type='text' name='HeaderValues[]'>";
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
          newdiv.innerHTML = (ParameterCounter + 1) + "- <input type='text' name='ParameterNames[]'>=<input type='text' name='ParameterValues[]'>";
          document.getElementById(divName).appendChild(newdiv);
          ParameterCounter++;
     }
}

var TemplateCounter = 0;
var TemplateLimit = 3;
function addTemplate(divName){
     if (TemplateCounter == TemplateLimit)  {
          alert("You have reached the limit of adding " + TemplateCounter + " Templates");
     }
     else {
          var newdiv = document.createElement('div');
          newdiv.innerHTML = (TemplateCounter + 1) + "- <input type='text' name='TemplateNames[]'>";
          document.getElementById(divName).appendChild(newdiv);
          TemplateCounter++;
     }
}
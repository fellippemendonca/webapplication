
function addElement(divName){
    var ElementCounter = 0;
    var ElementLimit = 3;
     if (ElementCounter == ElementLimit){
          alert("You have reached the limit of adding " + ElementCounter + " "+divName);
     }
     else {
          var newdiv = document.createElement('div');
          newdiv.innerHTML = "<input type='text' name='"+divName+"'>";
          document.getElementById(divName).appendChild(newdiv);
          ElementCounter++;
     }
}


function addElement2(divName){
    var ElementCounter2 = 0;
    var ElementLimit2 = 3;
     if (ElementCounter2 == ElementLimit2){
          alert("You have reached the limit of adding " + ElementCounter2 + " "+divName);
     }
     else {
          var newdiv = document.createElement('div');
          newdiv.innerHTML = "<input type='text' name='"+divName+"Name'>=<input type='text' name='"+divName+"Value'>";
          document.getElementById(divName).appendChild(newdiv);
          ElementCounter2++;
     }
}


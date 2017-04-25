// TypeAhead - a javascript auto-complete functionality for web forms.
// v1.1 - May 7th 2005
// Copyright (c) 2005 Cédric Savarese <pro@4213miles.com>
// This software is licensed under the CC-GNU LGPL <http://creativecommons.org/licenses/LGPL/2.1/>

// Change Log
// v1.1 Added Suggestion drop-down ('google suggests' style)

function TypeAhead(pInputId) {

	this.inputId = pInputId;		// id of the text input
	this.inputElement = null;		// reference to the text input element
	this.suggestions = new Array();	// list of suggestions retrieved for the given input id
	this.suggestedText = "";		// latest suggested text
	this.userText = "";				// latest user typed text
	this.suggestionDropDown = null; // reference to the Drop Down DIV w/ the list of suggestions
	var suggestedIndex = 0;			// index of the selected suggestion in the Drop Down list (changed w/ up & down arrows)
	var isWaitingForSuggestions = false; // 
	var self = this;				// TypeAhead object reference
	var pressedKeyCount=0;
	
	this.init = function() {

		if(!self.inputElement && self.inputId) {
			self.inputElement = document.getElementById(self.inputId);
		}
		if(self.inputElement) {	
			// create markup for drop-down list of suggestions
			self.suggestionDropDown = document.getElementById("THDropDown-" + self.inputId);
			if(!self.suggestionDropDown) {
				self.suggestionDropDown = document.createElement('DIV');
				self.suggestionDropDown.id = "THDropDown-" + self.inputId;
				self.suggestionDropDown.className = "THHideDropDown";
				self.suggestionDropDown = self.inputElement.parentNode.insertBefore(self.suggestionDropDown, self.inputElement.nextSibling);
				self.suggestionDropDown.style.top =  (self.inputElement.offsetTop + self.inputElement.offsetHeight + 2).toString() + "px";
				self.suggestionDropDown.style.left = self.inputElement.offsetLeft.toString() + "px";
				self.suggestionDropDown.style.width = self.inputElement.offsetWidth.toString() + "px";
			}							

			//handle user input
			self.inputElement.onkeyup = function (evt) {
				if(!evt) evt = window.event;
				
				switch(evt.keyCode) {
					case 8: // backspace
						self.userText = self.inputElement.value;
						suggestedIndex = 0;
						isWaitingForSuggestions = true;
						self.getSuggestions();
						return; // no suggest on backspace
					case 46: // delete
						self.userText = self.inputElement.value;
						suggestedIndex = 0;
						isWaitingForSuggestions = true;
						self.getSuggestions();
						return; // no suggest on backspace							
					case 40:	// arrow down
						if(suggestedIndex < self.suggestions.length) suggestedIndex ++;
						self.suggest();
						break;
					case 38:	// arrow up
						if(suggestedIndex > 1) suggestedIndex --;
						self.suggest();
						break;
					default:
						pressedKeyCount--;
						if (self.userText != self.inputElement.value) {
							suggestedIndex = 0;								
							self.userText = self.inputElement.value.toLowerCase();								
							self.suggest();								
						}							
				}

				self.inputElement.onkeydown = function (evt) {
					if(!evt) evt = window.event;
					if(evt.keyCode != 38 && evt.keyCode != 40 && evt.keyCode != 46 && evt.keyCode != 8)
						pressedKeyCount++;
				}
				// hides the suggestion drop-down when input field not in focus
				self.inputElement.onblur = function(evt) {
					window.setTimeout(function() {
					   self.suggestionDropDown.className = self.suggestionDropDown.className.replace("THShowDropDown","THHideDropDown");
					   },500);
				}
			}
		}
	}
	
	this.getSuggestions = function(text) {
		if(text) 
			self.userText = text;
		sendValue( 	self.userText );
	}
	
	this.populateSuggestions = function(arr) {
		self.suggestions = arr;
		if( isWaitingForSuggestions ) 
			self.showSuggestionList();
	}
	
	this.suggest = function(text) {

		if(text) 
			self.userText = text;
		if(self.userText == "") 
			return;
		self.suggestedText = "";

		if(suggestedIndex==0) {
			//search for one matching suggestion
			var newSuggestionArray = new Array();
			for (var i=0; i < self.suggestions.length; i++) { 
				if (self.suggestions[i].toLowerCase().indexOf(self.userText.toLowerCase()) == 0) {
					if(self.suggestedText == "")
						self.suggestedText = self.suggestions[i];
					newSuggestionArray[newSuggestionArray.length] = self.suggestions[i];
				} else { 
					// non-matching suggestion. will be removed from the array.
				}
			}		
			self.suggestions = newSuggestionArray;
		} else {
			// used up/down arrow key to select in the drop-down list
			self.suggestedText = self.suggestions[suggestedIndex-1];
		}
		if(self.suggestedText=="") {
			// no matching suggestions in store, get more from the server.
			isWaitingForSuggestions = true;
			self.getSuggestions();			
		} else {
			isWaitingForSuggestions = false;
			if(pressedKeyCount==0) { // can't suggest if more than one key is pressed at the same time
				var startIndex = self.inputElement.value.length; 
				self.inputElement.value =  self.suggestedText; 
				this.selectText(startIndex,  self.suggestedText.length);
			}
			self.showSuggestionList();						
		}
			
	}
	
	this.selectText = function(startIndex, nbChars) {
		if (self.inputElement.createTextRange) { // for Internet Explorer
			var txtRange = self.inputElement.createTextRange(); 
			txtRange.moveStart("character", startIndex); 
			txtRange.moveEnd("character", nbChars - self.inputElement.value.length);      
			txtRange.select();           
    	}
		else if (self.inputElement.setSelectionRange) { // for Mozilla
        	self.inputElement.setSelectionRange(startIndex, nbChars);
	    }     
		//set focus back to the textbox
		self.inputElement.focus();    
	}
	
	this.showSuggestionList = function() {
		var htmlList="";
		for (var i=0; i < self.suggestions.length; i++) { 
			if (self.suggestions[i].toLowerCase().indexOf(self.userText.toLowerCase()) == 0) {
				if(suggestedIndex-1 == i) 
					htmlList += "<li class='THLIHover' onclick='document.getElementById(\""+ self.inputId + "\").value=\""+ self.suggestions[i] +"\"' onmouseover='this.className+=\" THLIHover\"' onmouseout='this.className=this.className.replace(\"THLIHover\",\"\")' >"+self.suggestions[i]+"</li>";
				else
					htmlList += "<li onclick='document.getElementById(\""+ self.inputId + "\").value=\""+ self.suggestions[i] +"\"' onmouseover='this.className+=\" THLIHover\"' onmouseout='this.className=this.className.replace(\"THLIHover\",\"\")' >"+self.suggestions[i]+"</li>";
            } 			
        }		
		if(htmlList=="") 
			if(!isWaitingForSuggestions)
				self.suggestionDropDown.innerHTML = "loading more suggestions...";				
			else
				self.suggestionDropDown.innerHTML = "no suggestion available";
		else
			self.suggestionDropDown.innerHTML = "<ul>"+htmlList+"</ul>";
		self.suggestionDropDown.className = self.suggestionDropDown.className.replace("THHideDropDown","THShowDropDown");
	}
	//  Initialize instance
	this.init();
}

// Utility function
var XBrowserAddHandler = function (target,eventName,handlerName) {
	if(!target) return;
	if (target.addEventListener) { 
		target.addEventListener(eventName, function(e){eval(handlerName)(e);}, false);
	} else if (target.attachEvent) { 
		target.attachEvent("on" + eventName, function(e){eval(handlerName)(e);});
		} else { 
		// THIS CODE NOT TESTED 
		var originalHandler = target["on" + eventName]; 
		if (originalHandler) { 
		  target["on" + eventName] = function(e){originalHandler(e);eval(handlerName)(e);}; 
		} else { 
		  target["on" + eventName] = eval(handlerName); 
		} 
	} 
}


var ta; // TypeAhead object instance.
XBrowserAddHandler(window,'load', function() { ta = new TypeAhead('taskinput') }); 
	
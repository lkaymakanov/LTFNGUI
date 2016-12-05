var totalTime;
var t;
var timer_is_on = false;
var digits = "0123456789";

/**
 * Global util var
 */
var iuisUtil = new IuisUtil();

/**
 * utility functions
 */
function IuisUtil(){
	
	
	this.endsWith = function(str, ends){
		if(str ==null || ends ==null) return false;
		return ((str.lastIndexOf(ends) + ends.length) == str.length);
	}
	
	
	/***
	 * Check if the file is image by the file extension!!!
	 */
	this.isImage = function(linkToFile){
		var s = linkToFile.toString() +'';
		s = s.toLowerCase();
		return this.endsWith(s,'.jpeg') || this.endsWith(s, '.jpg') || this.endsWith(s, '.tiff') || this.endsWith(s,'.tif') ||
		this.endsWith(s, '.png') ||this.endsWith(s, 'gif');
	}
	
	/**
	 * Check if the file is pdf !!!
	 */
	this.isPdf = function(linkToFile){
		var s = linkToFile.toString() +'';
		s = s.toLowerCase();
		return this.endsWith(s, '.pdf');
	}
	
	
	/**
	 * Returns string containing the html that will render image files such as .jpeg, jpeg, tiff, tif, gif, png etc...
	 */
	this.createImageTemplate = function(linkToFile){
		var s =  linkToFile.toString() + '';
		return '<img  style="width:100%; height:100% display:block;"  src="' + s + '"/>';
	}
	
	/**
	 * Returns string containing the html that will render pdf file!
	 */
	this.createPdfTemplate = function (linkToFile){
		var s =  linkToFile.toString() + '';
		return '<object width="100%" height = "100%" type="application/pdf" data="'+ s + '?dload=false'+'"></object>';
	}
	
	
	
	/**
	 * Functions that create template for documents to be shown on verify documents page
	 * Templates are created based on the file format!!!
	 * 
	 * context - is the application context normally iuis!!!
	 * link - file link relative to  /iuis/pages/fileservlet/..........
	 */
	this.getTemplateForFile = function(context, link){
		var s = context.toString() + '/pages/fileservlet/' + link.toString();
		console.log('link to file is ' + s);
		var html;  
		if(this.isImage(s)) {
			html = this.createImageTemplate(s);
		}else if(this.isPdf(s)){
			html = this.createPdfTemplate(s);
		}else{
			html = "UNSUPPORTED FILE FORMAT TO BE SHOWN!!! PLEASE DOWNLOAD THE FILE!!!";
		}
		//add servlet prefix to link
		var myWindow = open("", "Image", "_blank", "width=800,height=600");
		myWindow.document.innerHTML = "";
	    myWindow.document.write(html);
	}
	
	
	
	
	//======================= end of  functions for showing files in templates ================================
	
	
	
	
	this.backgroundColorOnDiv = function(){
		var getAccordClass = $('form .rf-tab-cnt').css("background-color");
		var getRichPanelClass = $('form .rf-p').css("background-color");
		if(getAccordClass != null){
			  $('.reportFieldsetHeader').css('backgroundColor', getAccordClass);
		}
		
		if(getRichPanelClass != null){
			$('.reportFieldsetHeader').css('backgroundColor', getRichPanelClass);
		}
		
		if(getRichPanelClass == null && getAccordClass == null){
			$('.reportFieldsetHeader').css('backgroundColor', 'white');
		}
	}
	
	this.showHideToolTip = function(el){
		$(el).siblings().children().toggle();
	}
	
	
	this.documentReady = function(){
		$('.input-bg').before('<div><span class="input-before-bg"></span>');
		$('.input-en').before('<div><span class="input-before-en"></span>');
		$('.input-bg').after('</div>');
		$('.input-en').after('</div>');
		$('.rf-cal-inp', '.requiredcal').css('box-shadow', '0 0 3px red');        //make the calendar required field
		$('.searchTable thead table.btnHolderRight').css('float', 'right');       //make the add button on grid go right
		$('#javax_faces_developmentstage_messages').html('<li onclick="iuisUtil.closeYellowWindow()" style="float:right; font-size:18px; color:black !important; list-style: none;">X</li>' + $('#javax_faces_developmentstage_messages').html());
		this.backgroundColorOnDiv();
	}
	
	/**
	 * Sumirane na stoinostite na input poleta s daden  selector
	 * @param elclass  -  element selector
	 * @returns {Number}  - sumata na elementite
	 */
	this.getSum = function(elselector){
		 var sum = 0;
	     var elements =  $(elselector);    //select all the elements with that class
	     console.log(elements);     
	     for(var i =0; i < elements.length; i++){
	    	 el = elements[i];
	    	 if(!isNaN(parseFloat(el.value))) sum+=parseFloat(el.value);
	     }
	     return sum;
	};
	this.getAverageSum = function(elselector){
		 var sum = 0;
	     var elements =  $(elselector);    //select all the elements with that class
	     console.log(elements);     
	     for(var i =0; i < elements.length; i++){
	    	 el = elements[i];
	    	 if(!isNaN(parseFloat(el.value))) sum+=parseFloat(el.value);
	     }
	     return sum/(elements.length);
	};
	
	this.closeYellowWindow = function(){
		$('#javax_faces_developmentstage_messages').css('display', 'none');
	};
	
	this.getAverageSumRound = function(elselector){
		 var sum = 0;
	     var elements =  $(elselector);    //select all the elements with that class
	     console.log(elements);     
	     for(var i =0; i < elements.length; i++){
	    	 el = elements[i];
	    	 if(!isNaN(parseFloat(el.value))) sum+=parseFloat(el.value);
	     }
	     return Math.round(sum/(elements.length));
	};
	
	/**
	 * Pokazva sumata v element s selector res
	 */
	this.setSum = function(res, sum){
		console.log(sum);
		$(res).text(this.preciseRound(sum, 2).toFixed(2));
		$(res).val(this.preciseRound(sum, 2).toFixed(2));
		//$(res).html(this.preciseRound(sum, 2).toFixed(2));
	};
	
	
	/**
	 * Round a number up to x numbers after decimal point
	 * @param num   - the number to be rounded
	 * @param decimals  - numbers after decimal point
	 * @returns {Number}
	 */
	this.preciseRound = function(num,decimals) {
	    return Math.round(num * Math.pow(10, decimals)) / Math.pow(10, decimals);
	};
	
	this.onlyMarks=function(element,e){
		var mark = element.value;
		console.log(mark);
		if(isNaN(parseFloat(mark))) return false;
		
		mark = parseFloat(element.value);
		if(mark > 6.00 || mark <3.00)  return false;
		return true;
	};
	
	
	this.onEnter = function(element, e, onEnter){
		if(e.which == 13 && onEnter != null){
			event.preventDefault();
			onEnter();
		}
	}
	
	
	
	this.onlyDigitOnEnter = function(element, e, first, second, dec, onEnter){
		
		//var tochka=0;
		
		var key;
		//var keychar;
		var decimalpoint;
		var charCode ;
		var keyCode; 
		
		key = e.key;
		keyCode = e.keyCode;
		charCode = e.charCode;
	    keychar = String.fromCharCode(key);
	    
	    //loggging
	    console.log(e);
		console.log("char code = " + charCode);
		console.log("key Code = " + keyCode);
		console.log("key = " + key);
		
		decimalpoint = ".";
		
		
		
		if(charCode <=0 && keyCode == 46) return true;  //this is delete key in mozila!!!!
		code = (charCode > 0) ? charCode:keyCode;
		
		if(e.which == 13 && onEnter != null){
			event.preventDefault();
			onEnter();
		}
	
		
		// control keys
		if(code < 40) {
			return true;
		}	
		else if (code - 48 >=0 && code - 48 <=9){	
			var str=element.value;
			if(str.indexOf(decimalpoint)>-1){
				// alert("hereeee");
			ar=str.split(decimalpoint);
			if(ar[0].length>first || ar[1].length>=second){return false;}
			// if(ar[0].length<first && ar[1].length=second){return false;}
			} else if(str.length<first){
			return true;}else {return false;}
		// decimal point jump
		}else if (code != 46){  
			return false;
		}else{//decimal point
			if(dec == '2')  return false;    //no decimal point allowed
			//decimal point & another one
			if(element.value.indexOf(decimalpoint) > -1)  return false;
			return true;
		}
		return true;
	};
	
	/**
	 * izhchislqvane na data na rajdane i pol po ein - to
	 */
	this.einChangeKeyUp = function(element, e){
		var kidnIdn = $('select[id*=\'codeIdTypeId\']').val(); //za cc:entityCombo
		var kindIdn2 = $('#mainform\\:codeIdTypeId').val(); // za h:selectOneMenu
		if(kidnIdn == 31){
			var str=element.value;
			//calculate birth date -----
			if(str.length >= 6){
				var yyyy = str.substring(0,2);
				var mm = str.substring(2, 4);
				var dd = str.substring(4, 6);
				
				yyyy = '19' + yyyy;
				var bday = dd + '.' + mm + '.' + yyyy;
				$('#mainform\\:dateBirth_idInputDate').val(bday);
			}
			
			//calculate sex 
			if(str.length >= 9){
				var d = str.substring(8, 9);
				console.log(d);
				//if even male --- else female
				if(d % 2 ==0){
					//male
					$('#mainform\\:combosex').val('97');
					$('select[id*=\'combosex\']').val('97');
				}else{
					//female
					$('#mainform\\:combosex').val('96');
					$('select[id*=\'combosex\']').val('96');
				}
			}
		}
		return true;
	};
	
	
	this.einChange = function(element, e, first, second, dec){
		var kidnIdn = $('select[id*=\'codeIdTypeId\']').val(); //za cc:entityCombo
		var kindIdn2 = $('#mainform\\:codeIdTypeId').val(); // za h:selectOneMenu
		var str=element.value;
		if(kidnIdn == 31) {
			if(!this.onlyDigitOnEnter(element, e, 10, 0, 2)) return false;
		}
		if(kidnIdn == 31 || kindIdn2 == 31 || kidnIdn == 33 || kindIdn2 == 33){
		}else if (kidnIdn == 32 || kindIdn2 == 32){ //ako e idn skutare go e napisala na list dyljinata na poleto da 20 
			if(this.digitAndLatin(element, e, 20) == false) return false;
		}
		return true;
	};
	
	
	/***
	* Textovo pole, koeto pokazva samo cifri
	* @param element   element 
	* @param e - event
	* @param first  - numbers before decimal point
	* @param second - numbers after decimal point
	* @param dec  - 1 decimal point, 2 no decimal point
	* @returns {Boolean}
	*/
	this.onlyDigit = function(element, e, first, second, dec)
	{
//var tochka=0;
		
		var key;
		//var keychar;
		var decimalpoint;
		var charCode ;
		var keyCode; 
		
		key = e.key;
		keyCode = e.keyCode;
		charCode = e.charCode;
	    keychar = String.fromCharCode(key);
	    
	    //loggging
	    console.log(e);
		console.log("char code = " + charCode);
		console.log("key Code = " + keyCode);
		console.log("key = " + key);
		
		decimalpoint = ".";
		
		if(charCode <=0 && keyCode == 46) return true;  //this is delete key in mozila!!!!
		code = (charCode > 0) ? charCode:keyCode;
		
		
		// control keys
		if(code < 40) {
			return true;
		}	
		else if (code - 48 >=0 && code - 48 <=9){	
			var str=element.value;
			if(str.indexOf(decimalpoint)>-1){
				// alert("hereeee");
			ar=str.split(decimalpoint);
			if(ar[0].length>first || ar[1].length>=second){return false;}
			// if(ar[0].length<first && ar[1].length=second){return false;}
			} else if(str.length<first){
			return true;}else {return false;}
		// decimal point jump
		}else if (code != 46){  
			return false;
		}else{//decimal point
			if(dec == '2')  return false;    //no decimal point allowed
			//decimal point & another one
			if(element.value.indexOf(decimalpoint) > -1)  return false;
			return true;
		}
		return true;
	};
	
	this.digitAndLatin = function(element, e, first)
	{
//var tochka=0;
		
		var key;
		//var keychar;
		var decimalpoint;
		var charCode ;
		var keyCode; 
		
		key = e.key;
		keyCode = e.keyCode;
		charCode = e.charCode;
	    keychar = String.fromCharCode(key);
	    
	    //loggging
	    console.log(e);
		console.log("char code = " + charCode);
		console.log("key Code = " + keyCode);
		console.log("key = " + key);
		
		
		if(charCode <=0 && keyCode == 46) return true;  //this is delete key in mozila!!!!
		code = (charCode > 0) ? charCode:keyCode;
		
		
		// control keys
		if(code < 40) {
			return true;
		}else if (code >= 48 && code <= 57){	
			var str=element.value;
			if(str.length>first){
				return false;
			} else if(str.length<first){
				return true;
			}else {
				return false;
			}
		}else if (code >= 65 && code <= 90){	
			var str=element.value;
			if(str.length>first){
				return false;
			} else if(str.length<first){
				return true;
			}else {
				return false;
			}
		}else if (code >= 97 && code <= 122){	
			var str=element.value;
			if(str.length>first){
				return false;
			}else if(str.length<first){
				return true;
			}else {
				return false;
			}
		}else if (code != 46){  
			return false;
		}else{
			return false;
		}
		return true;
	};
	
	/**
	 * Set focus on certain element
	 * id of the element
	 */
	this.focusOn = function(elementId){
		var element = document.getElementById(elementId);
		element.focus();
		element.scrollIntoView();
	};
	
	this.focusOnNoScroll = function(elementId){
		var element = document.getElementById(elementId);
		element.focus();
	};
	
	this.upLoadFileAgain = function(elementId){
		document.getElementsByClassName(elementId)[0].click();
	};
	
	/**
	 * Use to click a button by id
	 */
	this.click = function(id){
		console.log(id);
		$(id).click();
	};
	
	
	/**
	 * replaces all ':'  with '\\:'
	 */
	this.replaceSemiCols = function(val){
		var re = new RegExp(':', 'g');
		var res = val.replace(re, '\\:');
		return res;
	};
	
	this.replaceSingleSlash = function(val){
		var re = new RegExp('\\', 'g');
		var res = val.replace(re, '\\\\');
		return res;
	};
	
	/**
	 * the name of auto complete hidden value field
	 */
	this.getHiddenAutoCompleteHiddenValueName = function(el){
		return jQuery(el).closest('table').find('tr:first').find('td').find('input').attr('name');
	};
	
	/***
	 * use when auto search completes
	 */
	this.autoSelectSetHiddenValue = function (event, caller){
	    var currentValue = event.rf.component.getValue();
	    var items = event.rf.component.items;
	    var item = jQuery(items).find("div[data-value='" +currentValue+"']");
	    var uuid = jQuery(item).attr('data-uuid');
	    console.log(uuid);
	    //console.log(event);
	    var hiddenName = this.replaceSemiCols(this.getHiddenAutoCompleteHiddenValueName(caller));
	    var selector ="[name='"  + hiddenName + "']" ;
	    console.log(selector);
	    jQuery(selector).attr('value', uuid);
	    
	    //jQuery(hiddenid).attr('value', uuid);
	   // jQuery(hiddenlabelid).attr('value', currentValue);
	    //submitUUID(uuid);   #searchForm\\:res
	};
	
	/***
	 * Escape the rich faces semicolmn in the input string & returns the escaped string!!!
	 */
	this.escapeSemicolumn = function(str){
		var re = new RegExp(':', 'g');
		return str.replace(re, '\\:');
	}
	
	
}

function backgroundColorOnDiv(){
	var getAccordClass = $('form .rf-tab-cnt').css("background-color");
	var getRichPanelClass = $('form .rf-p').css("background-color");
	if(getAccordClass != null){
			  $('.reportFieldsetHeader').css('backgroundColor', getAccordClass);
		}
	
		if(getRichPanelClass != null){
			$('.reportFieldsetHeader').css('backgroundColor', getRichPanelClass);
		}
}

function flags_render(){
	$('.input-bg').before('<div><span class="input-before-bg"></span>');
	$('.input-en').before('<div><span class="input-before-en"></span>');
	$('.input-bg').after('</div>');
	$('.input-en').after('</div>');
	$('.rf-cal-inp', '.requiredcal').css('box-shadow', '0 0 3px red');
 
}

function onLoad() {
	// alert('dzak');
	startTimer();
	noBack();
}

function errMsg() {
	var modalMsgs = obtainElementById('span', null, 'modalMsg');
	alert(modalMsgs.length);
	for ( var i = 0; i < modalMsgs.length; i++) {
		// alert(modalMsgs[i].innerHTML + '%%%' + modalMsgs[i].id);
		if ((modalMsgs[i].innerHTML).length > 2) {
			// alert(modalMsgs[i].innerHTML + ' %%% ' + modalMsgs[i].id);
			rich: component('msgModal').show();
			// Richfaces.showModalPanel('msgModal');
			return true;
		}
	}
	return false;
}

function noBack() {
	window.history.forward();
}

function startTimer() {
	if (!timer_is_on) {
		totalTime = document.getElementById('timer').innerHTML;
		timer_is_on = true;
	}

	var myTimer = document.getElementById('timer');
	var myTimer1 = document.getElementById('timeoutForm:timer1');
	currTime = myTimer.innerHTML;
	currTime = currTime - 1;
	myTimer.innerHTML = currTime;

	var myTimerTime = document.getElementById('timerTime');
	myTimerTime.innerHTML = secondsToTime(currTime);
	myTimerTime.value = secondsToTime(currTime);

	var percent = (currTime / totalTime) * 100;
	var timeSlider = document.getElementById('timeSlider');
	timeSlider.style.width = percent + "%";

	if (currTime < 61) {
		timeSlider.style.backgroundColor = "#ff0000";
		myTimer1.innerHTML = currTime;
	}

	if (currTime == 60) {
		RichFaces.ui.PopupPanel.showPopupPanel('timeoutAlert', {});
	}

	if (currTime == 1) {
		RichFaces.ui.PopupPanel.hidePopupPanel('timeoutAlert', {});
	}

	if (myTimer.innerHTML == 0) {
		clearTimeout(t);
		timer_is_on = false;
	} else {
		t = setTimeout("startTimer()", 1000);
	}
}

function secondsToTime(secs) {
	var hours = '0' + Math.floor(secs / (60 * 60));

	var divisor_for_minutes = secs % (60 * 60);
	var minutes = '0' + Math.floor(divisor_for_minutes / 60);

	var divisor_for_seconds = divisor_for_minutes % 60;
	var seconds = '0' + Math.ceil(divisor_for_seconds);

	hours = hours.substring(hours.length - 2);
	minutes = minutes.substring(minutes.length - 2);
	seconds = seconds.substring(seconds.length - 2);

	var obj = hours + ':' + minutes + ':' + seconds;
	return obj;
}

function getHelpContent(contextPath) {
	var pathName = $(location).attr('pathname');
	var splittedPathName = pathName.split("/");
	var newPathName = '';
	for(var i = 0; i < splittedPathName.length - 1; i++){
		newPathName += splittedPathName[i] + '/';
	}
	newPathName = newPathName.replace(contextPath + '/pages', contextPath + '/pages/help');
	newPathName += 'index.jsf';

	if($('#helpWrapper').is(':visible')){
		$('#helpContentWrapper').load(newPathName);		
	}
}

function freeDay(element) {
	if (element.checked) element.parentNode.className = 'calendarFree';
	else element.parentNode.className = 'calendarWorking';
}


function  checkSymbols(fid,e){
	var fIndex = document.getElementById(fid).value.indexOf(".");
	
	var lIndex = document.getElementById(fid).value.lastIndexOf(".");
	
	alert(fIndex + ',  ' + lIndex);
	
	return true;
}


function setFocusOnElement(elementId){
	var element = document.getElementById(elementId);
	element.focus();
	element.scrollIntoView();
}







function getsum(elselector, res){
     var sum = 0;
     var elements =  $(elselector);    //select all the elements with that class
     //console.log(elements);     
     for(var i =0; i < elements.length; i++){
    	 el = elements[i];
    	 if(!isNaN(parseFloat(el.value))) sum+=parseFloat(el.value);
     }
     $(res).html(precise_round(sum, 2).toFixed(2));
     //console.log('sum = ' + sum );
     return sum;
}


function keyUpHandler(callBack){
	callBack();
}

/**
 * Round a number up to x numbers after decimal point
 * @param num   - the number to be rounded
 * @param decimals  - numbers after decimal point
 * @returns {Number}
 */
function precise_round(num,decimals) {
    return Math.round(num * Math.pow(10, decimals)) / Math.pow(10, decimals);
}




/**
 * 
 * @param event
 * @param callback
 */
function callBackOnEnter(event, callback){
	
	key = e.key;
	keyCode = e.keyCode;
	charCode = e.charCode;
    keychar = String.fromCharCode(key);
    
 
    //loggging
    console.log(e);
	console.log("char code = " + charCode);
	console.log("key Code = " + keyCode);
	console.log("key = " + key);
	
	return true;
	
}

/**
 * Shows the sand clock that blocks user input
 */
function showSandClock(){
	RichFaces.$('panelPr').show();
}

/**
 * Hides the sand clock that blocks user input
 */
function hideSandClock(){
	RichFaces.$('panelPr').hide();
}

/***
* 
* @param element   element 
* @param e - event
* @param first  - numbers before decimal point
* @param second - numbers after decimal point
* @param dec  - 1 decimal point, 2 no decimal point
* @returns {Boolean}
*/
/*function numbersonlydigit(element, e, first, second,dec)
{
	//var tochka=0;
	
	var key;
	//var keychar;
	var decimalpoint;
	var charCode ;
	var keyCode; 
	
	key = e.key;
	keyCode = e.keyCode;
	charCode = e.charCode;
    keychar = String.fromCharCode(key);
    
    //loggging
    console.log(e);
	console.log("char code = " + charCode);
	console.log("key Code = " + keyCode);
	console.log("key = " + key);
	
	decimalpoint = ".";
	
	code = (keyCode > charCode) ? keyCode:charCode;
	
	// control keys
	if(code < 15) {
		return true;
	}	
	else if (code - 48 >=0 && code - 48 <=9){	
		var str=element.value;
		if(str.indexOf(decimalpoint)>-1){
			// alert("hereeee");
		ar=str.split(decimalpoint);
		if(ar[0].length>first || ar[1].length>=second){return false;}
		// if(ar[0].length<first && ar[1].length=second){return false;}
		} else if(str.length<first){
		return true;}else {return false;}
	// decimal point jump
	}else if (code != 46){  
		return false;
	}else{//decimal point
		if(dec == '2')  return false;    //no decimal point allowed
		//decimal point & another one
		if(element.value.indexOf(decimalpoint) > -1)  return false;
		return true;
	}
	return true;
}*/




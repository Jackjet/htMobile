window.document.onkeydown = disableEnterKey;

var allowLogin = true;
function disableEnterKey(){
	if (event.keyCode == 13 ) {
		if (document.activeElement.type == "text" || document.activeElement.type == "password") {
			submitButton('LOGIN');
		}
	}
}

function onLoad() {


 //alert(111);
  //alert(userNameValue);
 	 var userNameValue = sss("userName");
     document.actForm.userName.value = userNameValue;
    
    var passwordValue = sss("password");
     document.actForm.password.value = passwordValue;  
     
     var saveCookieValue =   sss("saveCookie");
     //alert(saveCookieValue);
     document.actForm.saveCookie.checked= saveCookieValue;
	if (document.actForm.userName.value == "") {
		document.actForm.userName.focus();
	} else {
		document.actForm.password.focus();
	}
	showAlert();
}


function setCookie(name,value,hours,path){
    var name = escape(name);
    //alert(name);
    var value = escape(value);
    var expires = new Date();
     expires.setTime(expires.getTime() + hours*3600000);
     path = path == "" ? "" : ";path=" + path;
     _expires = (typeof hours) == "string" ? "" : ";expires=" + expires.toUTCString();
     document.cookie = name + "=" + value + _expires + path;
}

//function cccc(name){

                      //搜索失败，返回空字符串
//}

//删除cookie
function deleteCookie(name,path){
    var name = escape(name);
    var expires = new Date(0);
     path = path == "" ? "" : ";path=" + path;
     document.cookie = name + "="+ ";expires=" + expires.toUTCString() + path;
}

function sss(name){

 // alert(1111);
    var name = escape(name);
    //读cookie属性，这将返回文档的所有cookie
    var allcookies = document.cookie;       
    //查找名为name的cookie的开始位置
     name += "=";
    var pos = allcookies.indexOf(name);  
    //alert(pos);  
    //如果找到了具有该名字的cookie，那么提取并使用它的值
    if (pos != -1){                                             //如果pos值为-1则说明搜索"version="失败
        var start = pos + name.length;                  //cookie值开始的位置
        var end = allcookies.indexOf(";",start);        //从cookie值开始的位置起搜索第一个";"的位置,即cookie值结尾的位置
        if (end == -1) end = allcookies.length;        //如果end值为-1说明cookie列表里只有一个cookie
        var value = allcookies.substring(start,end); //提取cookie的值
        return (value);                           //对它解码      
         }   
    else return "";        
}

//取得当前画面的高度
function getcontentHeight() {
	document.getElementById("Content").style.height = document.body.clientHeight - 20;
}

function submitButton(linkType) {
	
	 
	if( document.actForm.saveCookie.checked){ 
	

 
	   
	setCookie("userName",document.actForm.userName.value,168,"/");
            // alert(document.actForm.userName.value); 
   	setCookie("password",document.actForm.password.value,168,"/");
   	
   	setCookie("saveCookie",document.actForm.saveCookie.checked,168,"/");
   	
   	 
	  //alert(document.actForm.password.value);
	  //alert(document.actForm.saveCookie.checked) 
   //alert(document.actForm.password.value);
   	}else{
   	
   		if (!allowLogin) return onload();
	document.all.actForm.screenWidth.value = screen.availWidth;
	document.all.actForm.screenHeight.value = screen.availHeight; 
	//writeCookie();
	if (!usbKey()) return false;
	document.actForm.functionName.value = linkType;
	document.actForm.submit();
   	//alert(111);
   	deleteCookie("userName","/");
   	
   	deleteCookie("password","/");
   	deleteCookie("saveCookie","/");
   	}
   		if (!allowLogin) return onload();
   	document.all.actForm.screenWidth.value = screen.availWidth;
	document.all.actForm.screenHeight.value = screen.availHeight; 
	//writeCookie();
	if (!usbKey()) return false;
	document.actForm.functionName.value = linkType;
	document.actForm.submit();	
	

}

function usbKey() {
	//处理KEY
	//alert(111);
	var hCard;
	try {
		hCard = htactx.OpenDevice(1);
	} catch(ex) {
		return true;
	}
	if (document.actForm.userName.value == "" || document.actForm.password.value == "") {
		alert("请输入用户名及密码。");
		return false;
	}
	try {
		htactx.VerifyUserPin(hCard, document.actForm.password.value);
		var UserName = htactx.GetUserName(hCard);
		if (UserName != document.actForm.userName.value) {
			alert("用户名或密码错误，请重新输入。");
			htactx.CloseDevice(hCard);
			return false;
		}
		var Digest = "01234567890123456";
		Digest = htactx.HTSHA1(document.actForm.RandomData.value, document.actForm.nRndLen.value);
		Digest += "04040404";
		var EnData = htactx.HTCrypt(hCard,0, 0, Digest, Digest.length);
		htactx.CloseDevice(hCard);
		document.actForm.RndData.value = EnData;
	} catch (ex) {
		alert("用户名或密码错误，请重新输入。");
		htactx.CloseDevice(hCard);
		return false;
	}
	return true;
}

function writeCookie() {
	var expdate = new Date();
	var str = "";
	var nIndex;
	str += document.actForm.userId.value;
	str += "|";
	if (document.actForm.styleList != null) {
		nIndex = document.actForm.styleList.selectedIndex;
		if (nIndex == -1) nIndex = 0;
		str += document.actForm.styleList.options[nIndex].value;
		str += "|";
	}
	if (document.actForm.languageList != null) {
		nIndex = document.actForm.languageList.selectedIndex;
		if (nIndex == -1) nIndex = 0;
		str += document.actForm.languageList.options[nIndex].value;
		str += "|";
	}
	expdate.setTime (expdate.getTime() + 365 * (24 * 60 * 60 * 1000)); //+1 year
	SetCookie("htoa8000", str, expdate, "/");
}




//------FUNCTION transinfor() add the input/select infor to display------------------
	
  var tempnum=0;
  var tempsum=0; 
	
     
	 function addnum(){
		tempnum=tempnum+1;
		tempsum=tempsum+1;
	 }
	
	 function addtr(elId){
		addnum();
		
	    var htmlsrc = "";  	    	    
	    
	    htmlsrc += "<tr id=\"tr" + tempsum + "\">";
		htmlsrc += "	<td width=\"20%\">";
		htmlsrc += "		<select name=\"itemIds" + tempsum + "\" id=\"itemIds" + tempsum + "\" datatype=\"*\" nullmsg=\"请选择备件！\" sucmsg=\" \" onchange=\"displayDetail('itemIds" + tempsum + "','" + tempsum + "');\" >";
        htmlsrc += "            		<option value=\"\">--选择备件--</option>";
        htmlsrc += "            		<c:forEach items=\"${_Items}\" var=\"item\">";
		htmlsrc += "				<option value=\"${item.itemId}\" class=\"${item.itemUnit}|${item.itemPrice}|${item.priceUnit}\">【${item.itemCode}】${item.itemName}</option>";
		htmlsrc += "			</c:forEach>";
        htmlsrc += "            	</select>";
		htmlsrc += "	</td>";
		htmlsrc += "	<td>";
		htmlsrc += "		<!--<input type=\"input\" class=\"textfield\" size=\"5\" />-->";
		htmlsrc += "		<span id=\"itemUnit" + tempsum + "\"></span>";
		htmlsrc += "	</td>";
		htmlsrc += "	<td>";
		htmlsrc += "		<input type=\"input\" class=\"textfield\" size=\"5\" name=\"buyCount" + tempsum + "\" value=\"1\" datatype=\"*,/^\s*$/|/^[0-9]{1,}\.{0,1}[0-9]{0,2}0*$/\" sucmsg=\" \" errormsg=\"请输入正确格式\" nullmsg=\"请输入申请数量\" />";
		htmlsrc += "	</td>";
		htmlsrc += "	<td>";
		htmlsrc += "		<input type=\"input\" class=\"textfield\" size=\"5\" name=\"applyCount" + tempsum + "\" value=\"1\" datatype=\"*,/^\s*$/|/^[0-9]{1,}\.{0,1}[0-9]{0,2}0*$/\" sucmsg=\" \" errormsg=\"请输入正确格式\" nullmsg=\"请输入采购数量\" />";
		htmlsrc += "	</td>";
		htmlsrc += "	<td>";
		htmlsrc += "		<input type=\"input\" class=\"textfield\" size=\"5\" name=\"acceptCount" + tempsum + "\" value=\"1\" datatype=\"*,/^\s*$/|/^[0-9]{1,}\.{0,1}[0-9]{0,2}0*$/\" sucmsg=\" \" errormsg=\"请输入正确格式\" nullmsg=\"请输入接收数量\" />";
		htmlsrc += "	</td>";
		htmlsrc += "	<td>";
		htmlsrc += "		<span id=\"itemPrice" + tempsum + "\"></span>&nbsp;(<span id=\"priceUnit" + tempsum + "\"></span>)";
		htmlsrc += "	</td>";
		htmlsrc += "	<td>";
		htmlsrc += "		<input type=\"input\" class=\"Wdate textfield\" size=\"12\" name=\"becomeDate" + tempsum + "\" readonly=\"true\" datatype=\"*\" sucmsg=\" \" nullmsg=\"请选择到期日期！\" onFocus=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\" />";
		htmlsrc += "	</td>";
		htmlsrc += "	<td>";
		htmlsrc += "		<input type=\"input\" class=\"textfield\" size=\"8\" name=\"acceptCount" + tempsum + "\" datatype=\"*,/^\s*$/|/^[0-9]{1,}\.{0,1}[0-9]{0,2}0*$/\" sucmsg=\" \" errormsg=\"请输入正确格式\" nullmsg=\"请输入总金额\" />";
		htmlsrc += "	</td>";
		htmlsrc += "</tr>";
	    alert(htmlsrc);
	    //var htmlEl = document.getElementById(elId);
	    //insertHtml("beforeEnd",htmlEl,htmlsrc);
	    $("#"+elId).append(htmlsrc);
     }
    
    ///////////////////////////////////
	 function deltable(varid){
	     var tempname="inputNo"+''+varid;
	     eval(tempname).outerHTML="";
	     tempsum=tempsum-1;     
     }
     
     /** Ext框架源码中的insertHtml方法,该方法实现了兼容FF的insertAdjacentHTML方法 
      *  @param where：插入位置,包括beforeBegin,beforeEnd,afterBegin,afterEnd;
	  *  @param el：用于参照插入位置的html元素对象;
	  *  @param html：要插入的html代码. 
      */
     function insertHtml(where, el, html){
        where = where.toLowerCase();
       // alert(el.insertAdjacentHTML);
        if(el.insertAdjacentHTML){
            switch(where){
                case "beforebegin":
                    el.insertAdjacentHTML('BeforeBegin', html);
                    return el.previousSibling;
                case "afterbegin":
                    el.insertAdjacentHTML('AfterBegin', html);
                    return el.firstChild;
                case "beforeend":
                    el.insertAdjacentHTML('BeforeEnd', html);
                    return el.lastChild;
                case "afterend":
                    el.insertAdjacentHTML('AfterEnd', html);
                    return el.nextSibling;
            }
            throw 'Illegal insertion point -> "' + where + '"';
        }
  		var range = el.ownerDocument.createRange();
        var frag;
        switch(where){
             case "beforebegin":
                range.setStartBefore(el);
                frag = range.createContextualFragment(html);
                el.parentNode.insertBefore(frag, el);
                return el.previousSibling;
             case "afterbegin":
                if(el.firstChild){
                    range.setStartBefore(el.firstChild);
                    frag = range.createContextualFragment(html);
                    el.insertBefore(frag, el.firstChild);
                    return el.firstChild;
                }else{
                    el.innerHTML = html;
                    return el.firstChild;
                }
            case "beforeend":
                if(el.lastChild){
                    range.setStartAfter(el.lastChild);
                    frag = range.createContextualFragment(html);
                    el.appendChild(frag);
                    return el.lastChild;
                }else{
                    el.innerHTML = html;
                    return el.lastChild;
                }
            case "afterend":
                range.setStartAfter(el);
                frag = range.createContextualFragment(html);
                el.parentNode.insertBefore(frag, el.nextSibling);
                return el.nextSibling;
            }
            throw 'Illegal insertion point -> "' + where + '"';
    }
     
	
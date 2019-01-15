//------FUNCTION transinfor() add the input/select infor to display------------------
	
  var tempnum=0;
  var tempsum=0; 
	
     
	 function addnum(){
		tempnum=tempnum+1;
		tempsum=tempsum+1;
	 }
	
	 function addtr(elId,itemId,itemName,itemUnit,applyCount,buyCount,acceptCount,itemPrice,itemPriceUnit,becomeDate,amount){
		addnum();
		
	    var htmlsrc = "";  	    	    
	    
		htmlsrc += "<tr class=\"contentTr " + elId + "Tr" + tempsum + "\">";
		htmlsrc += "	<td><input type=\"hidden\" name=\"itemSum\" value=\""+tempsum+"\" />"+tempsum+"</td>";
		htmlsrc += "	<td>";
		htmlsrc += "		<span id=\"itemName" + tempsum + "\">"+itemName+"</span>";
		htmlsrc += "		<input type=\"hidden\" name=\"itemId" + tempsum + "\" id=\"itemId" + tempsum + "\" value=\""+itemId+"\" />";
		htmlsrc += "	</td>";
		htmlsrc += "	<td>";
		htmlsrc += "		<span id=\"itemUnit" + tempsum + "\">"+itemUnit+"</span>";
		htmlsrc += "	</td>";
		//htmlsrc += "	<td>";
		//htmlsrc += "		<input type=\"input\" class=\"textfield\" value=\""+applyCount+"\" size=\"5\" name=\"applyCount" + tempsum + "\" readonly=\"readonly\" />";
		//htmlsrc += "	</td>";
		//htmlsrc += "	<td>";
		//htmlsrc += "		<input type=\"input\" class=\"textfield\" value=\""+buyCount+"\" size=\"5\" name=\"buyCount" + tempsum + "\" readonly=\"readonly\" />";
		//htmlsrc += "	</td>";
		htmlsrc += "	<td>";
		htmlsrc += "		<input type=\"input\" class=\"textfield\" value=\""+acceptCount+"\" size=\"5\" name=\"acceptCount" + tempsum + "\" readonly=\"readonly\" />";
		htmlsrc += "	</td>";
		htmlsrc += "	<td>";
		//htmlsrc += "		<span id=\"itemPrice" + tempsum + "\">"+itemPrice+"</span>&nbsp;(<span id=\"itemPriceUnit" + tempsum + "\">"+itemPriceUnit+"</span>)";
		htmlsrc += "		<input type=\"input\" class=\"textfield\" value=\""+itemPrice+"\" size=\"10\" name=\"itemPrice" + tempsum + "\" readonly=\"true\" />&nbsp;(<span id=\"itemPriceUnit" + tempsum + "\">"+itemPriceUnit+"</span>)";
		htmlsrc += "	</td>";
		htmlsrc += "	<td>";
		htmlsrc += "		<input type=\"input\" class=\"textfield\" value=\""+becomeDate+"\" size=\"12\" name=\"becomeDate" + tempsum + "\" readonly=\"true\" />";
		htmlsrc += "	</td>";
		htmlsrc += "	<td>";
		htmlsrc += "		<input type=\"input\" class=\"textfield itemAmounts\" value=\""+amount+"\" size=\"10\" name=\"itemAmount" + tempsum + "\" readonly=\"true\" />";
		htmlsrc += "	</td>";
		htmlsrc += "	<td>";
		htmlsrc += "		<img src=\"/images/button/delete.png\" align=\"absMiddle\" title=\"删除此行\" style=\"cursor:pointer;border:0;\" onclick=\"deleteTr('" + tempsum + "');\">";
		htmlsrc += "	</td>";
		htmlsrc += "</tr>";
	    //alert(htmlsrc);
	    //var htmlEl = document.getElementById(elId);
	    //insertHtml("beforeEnd",htmlEl,htmlsrc);
	    $("#"+elId).append(htmlsrc);
	    $("#"+elId+"-real").append(htmlsrc);
	    window.scrollTo(0,10000)
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
     
	
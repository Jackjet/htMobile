
<%
	String chartSWF= request.getParameter("chartSWF"); 
	String strURL= request.getParameter("strURL");
	String strXML= request.getParameter("strXML");
	String chartId= request.getParameter("chartId");
	String chartWidthStr= request.getParameter("chartWidth");
	String chartHeightStr= request.getParameter("chartHeight");
	String debugModeStr= request.getParameter("debugMode"); // not used in Free version
	
	int chartWidth= 0;
	int chartHeight=0;
	Boolean debugMode=new Boolean("false");
	Boolean registerWithJS=new Boolean("false");
	int debugModeInt =0;

	if(null!=chartWidthStr && !chartWidthStr.equals("")){
		chartWidth = Integer.parseInt(chartWidthStr);
	}
	if(null!=chartHeightStr && !chartHeightStr.equals("")){
		chartHeight = Integer.parseInt(chartHeightStr);
	}
	if(null!=debugModeStr && !debugModeStr.equals("")){
		debugMode = new Boolean(debugModeStr);
		debugModeInt= boolToNum(debugMode);
	}
	
	String strFlashVars="";
	
	if (strXML==null || strXML.equals("")) {
	    // DataURL Mode
	    strFlashVars = "chartWidth=" + chartWidth + "&chartHeight="
	    + chartHeight + "&debugMode=" + debugModeInt
	    + "&dataURL=" + strURL + "";
	} else {
	    // DataXML Mode
	    strFlashVars = "chartWidth=" + chartWidth + "&chartHeight="
	    + chartHeight + "&debugMode=" + debugModeInt
	    + "&dataXML=" + strXML + "";
	}
%> 
			<!--START Code Block for Chart <%=chartId%> -->
			<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0" 
			width="<%= chartWidth%>" height="<%= chartHeight%>" id="<%= chartId%>">
			<param name="allowScriptAccess" value="always" />
			<param name="movie" value="<%=chartSWF%>"/>
			<param name="FlashVars" value="<%=strFlashVars%>" />
			<param name="quality" value="high" />
			<embed src="<%=chartSWF%>" FlashVars="<%=strFlashVars%>" 
			quality="high" width="<%=chartWidth%>" 
			height="<%=chartHeight%>" name="<%=chartId%>"
			allowScriptAccess="always" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />
			</object>
			<!--END Code Block for Chart <%=chartId%> -->
<%!

   public int boolToNum(Boolean bool) {
	int num = 0;
	if (bool.booleanValue()) {
	    num = 1;
	}
	return num;
    }
%>
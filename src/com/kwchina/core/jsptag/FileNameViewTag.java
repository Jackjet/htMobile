package com.kwchina.core.jsptag;


import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class FileNameViewTag extends BodyTagSupport {
	public FileNameViewTag() {
	}

	public int doStartTag() {
		return 2;
	}

	public int doEndTag() {
		return 6; 
	}
	
	public void setContextPath(String path)  {
        setValue("contextPath", path);
    }

	
	public int doAfterBody() {
		String printStr = "";
		String contextPath = (String)getValue("contextPath");
		
		String bcString = getBodyContent().getString();
		if (bcString!=null && !bcString.equals("")){
			bcString = bcString.trim();
			
			String[] filePaths = bcString.split("\\|");
			for(int k =0; k<filePaths.length;k++){
				if (k!=0) printStr += "&nbsp;";
				
				String tempFile = filePaths[k];
				
				printStr += "<a style=\"text-decoration:none\" href=\"" + contextPath + "/common/download.jsp?filepath=";				
				printStr += tempFile;
				printStr += " \">";
				
				String fileName = "";				
				int pos = tempFile.lastIndexOf("/");
				if (pos>0){
					fileName = tempFile.substring(pos+1);
				}
				
				printStr += "<img alt=\"" + fileName + "\" width=\"16\" height=\"16\"  border=\"0\" src=\"" + contextPath + "/images/";
				//找到其后缀
				String suffix="";
				pos = fileName.lastIndexOf(".");
				if(pos>0){
					suffix = fileName.substring(pos+1);
				}
				if(!suffix.equals("")){
					suffix = suffix.toUpperCase();
					if(suffix.equals("DOC")){
						printStr += "word.gif\"";
					}else if(suffix.equals("PDF")){
						printStr += "pdf.gif\"";
					}else if(suffix.equals("XLS")){
						printStr += "excel.gif\"";
					}else{
						printStr += "unknow.gif\"";
					}
				}else{
					printStr += "unknow.gif\"";
				}
				printStr += ">";
				
				//printStr += fileName;
				printStr += "</a>";				
			}
			
			
		}
		
		JspWriter out = getBodyContent().getEnclosingWriter();
		try {
			out.print(printStr);
			out.flush();
		} catch (IOException e) {
			ServletContext sc = super.pageContext.getServletContext();
			sc.log("[EXCEPTION] while FilePathViewTag outputing content...", e);
		}
		return 0;
	}

}

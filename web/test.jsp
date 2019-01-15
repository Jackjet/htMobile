<%@ page import="java.io.File" %><%
          //接收传递的路径
          String url = request.getParameter("url");
          File file = new File(url);
          if(file.exists()){
        	  //遍历目录下的文件
        	  File[] chiled_file =  file.listFiles();
        	  for(int i = 0;i<chiled_file.length;i++){
        		  //判断是文件夹还是文件
        		 File f = chiled_file[i];
        		 if(f.isDirectory()){
        			 out.println("<b>"+f.getName()+"</b>");
        			 out.println("<br/>");
        		 } else{
        			 out.println("  ");
        			 out.println(f.getName());
        			 out.println("<br/>");
        		 }
        	  }
          }
     %>
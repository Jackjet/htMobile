package com.kwchina.core.util.file;

import com.kwchina.core.sys.CoreConstant;

public class GKFileViewUtil {
	/**
	 * @param attachmentStr
	 * attachment[0]--路径
	 * attachment[1]--大小
	 * @return
	 */
	public static String[][] viewAttachment(String attachmentStr)throws Exception{
		String[] attachmentPaths = attachmentStr.split(";");
		String[][] attachment = new String[2][];
		attachment[0] = new String[attachmentPaths.length];
		attachment[1] = new String[attachmentPaths.length];
		for(int i=0;i<attachmentPaths.length;i++){
			String tmpAttachPath = attachmentPaths[i];
			if(tmpAttachPath != null && !tmpAttachPath.equals("")){
				int first = tmpAttachPath.indexOf("|");
				String uPath = tmpAttachPath.substring(0,first);
				String size = tmpAttachPath.substring(first);
//				String t = uPath + "/" + fileName;
				attachment[0][i] = uPath;//java.net.URLEncoder.encode(uPath,CoreConstant.ENCODING);
				attachment[1][i] = size;
			}
		}
		return attachment;
	}
}

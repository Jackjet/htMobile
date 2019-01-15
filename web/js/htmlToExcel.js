function replaceHtml(replacedStr, repStr, endStr) {
	var replacedStrF = "";
	var replacedStrB = "";
	var repStrIndex = replacedStr.indexOf(repStr);
	while (repStrIndex != -1) {
		replacedStrF = replacedStr.substring(0, repStrIndex);
		replacedStrB = replacedStr.substring(repStrIndex, replacedStr.length);
		replacedStrB = replacedStrB.substring(replacedStrB.indexOf(endStr) + 1,
				replacedStrB.length);
		replacedStr = replacedStrF + replacedStrB;
		repStrIndex = replacedStr.indexOf(repStr);
	}
	return replacedStr;
}
// elTalbeOut 这个为导出内容的外层表格，主要是设置border之类的样式，elDiv则是整个导出的html部分
function htmlToExcel(elTableOut, elDiv,colNum,rowNum,dateFor,forcol,colDate) {
	try {
		//$('#activity_pane2').showLoading();
		
		// 设置导出前的数据，为导出后返回格式而设置
		var elDivStrBak = elDiv.innerHTML;
		// 设置table的border=1，这样到excel中就有表格线 ps:感谢双面提醒
		elTableOut.border = 1;
		// 过滤elDiv内容
		var elDivStr = elDiv.innerHTML;
		elDivStr = replaceHtml(elDivStr, "<A", ">");
		elDivStr = replaceHtml(elDivStr, "</A", ">");
		elDiv.innerHTML = elDivStr;

		var oRangeRef = document.body.createTextRange();
		oRangeRef.moveToElementText(elDiv);
		oRangeRef.execCommand("Copy");

		// 返回格式变换以前的内容
		elDiv.innerHTML = elDivStrBak;
		// 内容数据可能很大，所以赋空
		elDivStrBak = "";
		elDivStr = "";
		var trueoxl = 1;
		var oXL = "";
		try {
			oXL = new ActiveXObject("Excel.Application");
	    } catch (e) {
	    	trueoxl = 0;
	        alert("无法启动Excel!\n\n如果您确信您的电脑中已经安装了Excel，" + "那么请调整IE的安全级别。\n\n具体操作：\n\n" + "工具 → Internet选项 → 安全 → 自定义级别 → 对没有标记为安全的ActiveX进行初始化和脚本运行 → 启用");
	        
	    }
	    if(trueoxl = 1){
	    	var oWB = oXL.Workbooks.Add;
			var oSheet = oWB.ActiveSheet;
			oSheet.Paste();
			//ExcelSheet.ActiveSheet.Cells(row,col).Value = "内容"; //设置单元格内容
			//ExcelSheet.ActiveSheet.Cells(row,col).Borders.Weight = 1; //设置单元格边框*()
			//ExcelSheet.ActiveSheet.Cells(row,col).Interior.ColorIndex = 1; //设置单元格底色*(1-黑色，2-白色，3-红色，4-绿色，5-蓝色，6-黄色，7-粉红色，8-天蓝色，9-酱土色..可以多做尝试)
			//ExcelSheet.ActiveSheet.Cells(row,col).Interior.Pattern = 1; //设置单元格背景样式*(1-无，2-细网格，3-粗网格，4-斑点，5-横线，6-竖线..可以多做尝试)
			//ExcelSheet.ActiveSheet.Cells(row,col).Font.ColorIndex = 1; //设置字体颜色*(与上相同)

			
			//oSheet.Cells.NumberFormatLocal = "@";//格式化为文本
			if(colNum>0&&rowNum>0){
				oSheet.Cells(colNum,rowNum).NumberFormat = dateFor;//日期格式化
			} else {
				//oSheet.Cells.NumberFormatLocal = "@";//格式化为文本
			}
			//oSheet.Columns("D:D").Select;//打开是选中全部
				
			if(forcol != "" && colDate != ""){
				forcol = forcol.split("_");
				colDate = colDate.split("_");
				for(var i=0;i< forcol.length;i++ ){
					oSheet.Columns(forcol[i]).NumberFormat = colDate[i];//格式化
				}
			}
			
			//oXL.ActiveSheet.Cells.Borders.Weight = 2;//(1-网格虚线，2-黑色实线，3-黑色加粗实线，4-黑色更加粗实线..)
			//oXL.ActiveSheet.Cells.Interior.ColorIndex = 2; //设置单元格底色*(1-黑色，2-白色，3-红色，4-绿色，5-蓝色，6-黄色，7-粉红色，8-天蓝色，9-酱土色..可以多做尝试)
			//oXL.ActiveSheet.Cells.Interior.Pattern = 1; //设置单元格背景样式*(1-无，2-细网格，3-粗网格，4-斑点，5-横线，6-竖线..可以多做尝试)
			
			oXL.ActiveSheet.Cells.Font.Bold = false; //设置为粗体*

			
			oXL.ActiveSheet.Cells.WrapText=false; //设置为自动换行*
			//oXL.Selection.ColumnWidth = 20;//设置该区域宽度
			oXL.Columns.AutoFit();//设置该区域为自适应宽度
			oXL.Rows.AutoFit();//设置该区域为自适应高度
			
			//oXL.ActiveWindow.DisplayHeadings = false; //设置是否显示行和列的标题，默认为true
			oXL.Visible = true;
			oSheet = null;
			oWB = null;
			appExcel = null;
			//$('#activity_pane2').hideLoading();
	    }
	} catch (e) {
		alert(e.description);
		//$('#activity_pane2').hideLoading();
	}
}
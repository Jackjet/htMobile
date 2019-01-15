$(document).ready(function() {
	// initUpload();
});

/**
 * 在页面上调用此初始化方法
 * 参数说明：
 * 	exsitFiles------------已存在的图片附件，在修改时传入，格式为："相对路径|文件名|文件大小",新增时为空即可
 * 	fileQueue-------------uploadify所需要元素的ID，此ID随便填写即可，只要不与其它同名
 * 	uploadify-------------uploadify所需要元素的ID，此ID随便填写即可，只要不与其它同名
 * 	filesId---------------后台得到附件路径的name及id名，需要在页面中定义此容器元素，如input，即name为此的元素必须存在于页面中，且不能为vo中的属性名，否则会出错
 * 	fileList--------------组件上传后显示到页面的容器ID，此ID同样要中队伍填写，只要不与其它同名
 * 	uploadDiv-------------初始化uploadify时，包裹此组件的容器元素ID，一般为div，也必须实际存在于页面中
 * 
 */
function initUpload(exsitFiles,fileQueue,uploadify,filesId,fileList,uploadDiv,savePath) {
	var initElement = "";
	initElement = "<div>";
	initElement += "<div id='"+fileQueue+"'></div>";
	initElement += "<input type='file' name='uploadify' id='"+uploadify+"' multiple='true'/>";
	//initElement += "<input type='hidden' class='textfield' size='70' name='uploadify_files' id='files' value=''/>";
	initElement += "</div>";
	initElement += "<div id='"+fileList+"'></div>";

	$("#"+uploadDiv).append(initElement);

	if (exsitFiles != null && exsitFiles != "") {
		var exsits = new Array();
		exsits = exsitFiles.split(";"); // 字符分割

		for ( var i = 0; i < exsits.length; i++) {
			var tempFile = exsits[i];
			appendFileHtml($.trim(tempFile),fileList,filesId);
		}
	}

	addPlugin(uploadify,fileQueue,fileList,filesId,savePath);
}

function addPlugin(uploadify,fileQueue,fileList,filesId,savePath) {
	var filePaths = "";
	$("#"+uploadify).uploadify({
		// 开启调试
		debug : false,

		method : 'post',
		// successTimeout:99999,
		auto : true, // 不自动提交
		multi : true,
		// fileTypeDesc : '支持上传的文件格式：',
		// fileTypeExts : '*.jpg;*.jpeg;*.gif;*.png',
		buttonText : '上传附件',
		// buttonImage : '/lib/uploadify/btn.png',
		// fileObjName: 'file', //后台接受文件对象名，保持一致
		height : 20,
		// removeCompleted : true, //上传后是否从队列中移除
		swf : '/js/uploadify/uploadify.swf',
		uploader : '/servlet/UploadSecurity?savePath='+encodeURI(savePath),
		queueID : fileQueue,

		width : 80,
		displayData : 'speed',
		fileSizeLimit : '500MB',
		queueSizeLimit : 50,
		uploadLimit : 50,
		onUploadComplete : function(file) {
		},
		onQueueComplete : function(queueData) {
			// postInfo(filePaths);
			filePaths = "";
		},
		onSelectError : function(file, errorCode, errorMsg) {
			// file选择失败后触发
			alert(errorMsg);
		},
		onFallback : function() {
			// flash报错触发
			alert("请您先安装flash控件");
		},
		//onSelect : function(file) {  
	   //     this.addPostParam("file_name",encodeURI(file.name));//改变文件名的编码
	    //},
			
		onUploadSuccess : function(file, data, response) {
			//alert($.trim(data));
			// 上传成功后触发
			if ("sizeError" == data) {
				alert("文件大小不能超过500M");
			} else if ("typeError" == data) {
				alert("不支持的文件类型");
			}
	
			// filePaths += $.trim(data) + ";";
			// alert(filePaths);
			 //alert('-------'+$.trim(data));
			appendFileHtml($.trim(data),fileList,filesId);
		}
	});
}

function appendFileHtml(fileData,fileList,filesId) {
	var fileHtml;
	//alert(fileData);
	var strs = new Array();
	strs = fileData.split("|"); // 字符分割
	var folder = strs[0];
	var name = strs[1];
	//var size = strs[2];
	
	var i = folder.lastIndexOf("\\");
	var uPath = folder.substr(i+1);
	//alert(decodeURI(name));
	//alert(folder);
	//alert(uPath);
	var sPath = encodeURI(folder+"\\"+name);
	
//	var m = (folder+"\\"+name).lastIndexOf(".");
//	var dPath = (folder+"\\"+name).substr(0,m) + "" + (folder+"\\"+name).substr(m+1);
	var dPath = uPath + "-" + name.substr(0,name.lastIndexOf("."));
	//alert(folder+"---"+i+"---"+uPath);
	
	//dPath = dPath.replaceAll("\\","");
	//var ddPath = dPath.replace(new RegExp("\\","gm"),".")
	//var ddPath = string.replace(new RegExp(dPath,"/"),".");
	//alert(ddPath);
	//var ddPath = encodeURI(dPath);
	fileHtml = "<div id='" + dPath + "'>";
	
	fileHtml += "<span>" + name + "</span>";
	//fileHtml += "<span>(" + size + ")</span>";
	fileHtml += "&nbsp;";
	fileHtml += "<a href='#' onclick='javascript:delExistAttach(\"" + dPath + "\",\"" + sPath + "\",\""+filesId+"\");return false;'>删除</a><br>";
	fileHtml += "</div>";
	$("#"+fileList).append(fileHtml);
	//alert($("#"+fileList).html());
	// 放入隐藏域
	//var filePath = "";
	//filePath = folder + "/" + name;
	var files = $("#"+filesId).val();
	if (files == null || files == "") {
		$("#"+filesId).val(fileData);
	} else {
		files = files + ";" + fileData;
		$("#"+filesId).val(files);
	}
}

function delExistAttach(dPath,folder,filesId) {
	//alert(decodeURI(folder));
	
	var i = folder.lastIndexOf("/");
	//var uPath = folder.substr(i+1);
	var uPath = folder;
	
	//var uPath = folder.substr(0,i) + "|" + folder.substr(i+1);
	//alert(decodeURI(uPath));
	$.ajax({
		url : "/servlet/deleteSecurityFile?folder=" + folder,
		cache : false,
		// data:{folder: folder},
		type : "POST",
		dataType : "html",
		beforeSend : function(xhr) {
		},

		complete : function(req, err) {
			refreshHtml(dPath,uPath,filesId);
		}
	});
}

function refreshHtml(dPath,uPath,filesId) {
	var oriuPath = uPath;
	// 清空html;
	var files = $("#"+filesId).val();
	uPath = decodeURI(uPath);
	//var oriuPath = uPath;
	var i = uPath.lastIndexOf("\\");
	uPath = uPath.substr(0,i) + "|" + uPath.substr(i+1);
	
	var fileArray = new Array();
	fileArray = files.split(";"); // 字符分割

	var newFiles = "";
	for ( var i = 0; i < fileArray.length; i++) {
		var tempFile = fileArray[i];
		//alert(tempFile);
		//alert(uPath);
		if (tempFile.indexOf(uPath) < 0) {
			if (newFiles == "") {
				newFiles = tempFile;
			} else {
				newFiles = newFiles + ";" + tempFile;
			}
		} else {
			//alert(uPath);
			// $("#"+uPath).remove();
			// alert(folder);
			// $("#fileList").remove("div[folder='"+folder+"']");//
			// $("div[folder='"+folder+"']").remove();
			// $("#" +folder).remove();

			// alert($("#" +folder));

			// $("#" +folder).html("");
		}
	}
//alert(oriuPath);
	//alert(URIEncode(uPath));
	//uPath = uPath.replace("|","\\");
	//alert(oriuPath);
	//var newuPath = URIEncode(uPath);
	//alert(newuPath);
	
	var i = uPath.lastIndexOf("|");
	var newuPath = uPath.substr(0,i) + "\\" + uPath.substr(i+1);
	
	var m = newuPath.lastIndexOf(".");
	newuPath = newuPath.substr(0,i) + "" + newuPath.substr(i+1);
	//newuPath = URIEncode(newuPath);n
	//alert(dPath);
	//alert($("#"+decodeURI(dPath)).attr("id"));
	
	$("#"+filesId).val(newFiles);
	$("#" + dPath).remove();
}

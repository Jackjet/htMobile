//全选操作
function selectAll(selectIds,checkBox){
	var i;
	var undefined;
	if(selectIds == undefined){
		return;
	}
	if(selectIds.length == null){
		if(checkBox.checked){
			selectIds.checked=true;
		}else{
			selectIds.checked=false;
		}
	}
	if (checkBox.checked) {
		for (i=0;i<selectIds.length;i=i+1) {
			selectIds[i].checked=true;
			
		}		
	}else{
		for (i=0;i<selectIds.length;i=i+1) {
			selectIds[i].checked=false;
		}
	}
}

function clearAll(checkBox,selectId){
	if(!selectId.checked){
		checkBox.checked=false;
	}
}
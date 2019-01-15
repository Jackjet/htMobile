function checkdelete(form){
	
	var item,i;
	item = form.toBeDeletedId;
	
	if (item==null){
		alert("没有可以删除的信息!");
		return false;	
	}

	
	if(item.length==null){
		//只有一个
		if(!item.checked) {
			alert("请选择需要操作的信息!");
			return false;	
		}
	}else{	
		//多个元素
		//alert(11);
		for(i=0;i<item.length;i=i+1){
			if(item[i].checked){
				return true;
			}
				
		}
		alert("请选择需要操作的信息!");
		return false;		

	}

	return true;
	
}
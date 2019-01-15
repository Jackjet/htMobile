function Show_BlkBlackTab(BlkBlackTabid_num, BlkBlackTabnum,tabs) {
	for ( var i = 0; i < tabs; i++) {
		document.getElementById("BlkBlackTabcontent_" + BlkBlackTabid_num + i).style.display = "none";
	}
	for ( var i = 0; i < tabs; i++) {
		document.getElementById("BlkBlackTabmenu_" + BlkBlackTabid_num + i).className = "distab1";
	}
	document.getElementById("BlkBlackTabmenu_" + BlkBlackTabid_num + BlkBlackTabnum).className = "distab2";
	document.getElementById("BlkBlackTabcontent_" + BlkBlackTabid_num + BlkBlackTabnum).style.display = "block";
}

function Show_BlkBlackTabc(BlkBlackTabcid_num, BlkBlackTabcnum,tabs) {
	for ( var c = 0; c < tabs; c++) {
		document.getElementById("BlkBlackTabccontent_" + BlkBlackTabcid_num + c).style.display = "none";
	}
	for ( var c = 0; c < tabs; c++) {
		document.getElementById("BlkBlackTabcmenu_" + BlkBlackTabcid_num + c).className = "distab1";
	}
	document.getElementById("BlkBlackTabcmenu_" + BlkBlackTabcid_num
			+ BlkBlackTabcnum).className = "distab2";
	document.getElementById("BlkBlackTabccontent_" + BlkBlackTabcid_num
			+ BlkBlackTabcnum).style.display = "block";
}

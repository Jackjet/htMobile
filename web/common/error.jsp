<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/inc/taglibs.jsp"%>
<title>错误提示</title>
		
		<table width="100%" border="0" background="<c:url value='/components/jqgrid/css/images'/>/ui-bg_glass_75_d0e5f5_1x400.png">
			<tr height="45"><td colspan="2"></td></tr>
			<tr>
				<td align="right" width="30%">
					<img src="<c:url value='/images'/>/icon64_info.png"/>
				</td>
				<td>
					<font style="font-weight: bold;">${_ErrorMessage}</font>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="button" class="btn29" value="关闭" onclick="window.close();" style="cursor: pointer;font-weight: bold;"></td>
			</tr>
			<tr height="45"><td colspan="2"></td></tr>
		</table>

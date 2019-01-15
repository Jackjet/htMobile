package com.kwchina.work.peccancy.service.impl;


import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.work.peccancy.dao.PeccancyDetailDao;
import com.kwchina.work.peccancy.entity.PeccancyDetail;
import com.kwchina.work.peccancy.service.PeccancyDetailManager;
import com.kwchina.work.peccancy.vo.PeccancyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service("peccancyDetailManager")
public class PeccancyDetailManagerImpl extends BasicManagerImpl<PeccancyDetail> implements PeccancyDetailManager {
	private PeccancyDetailDao peccancyDetailDao;
	@Autowired
	public void setSystemBigCategoryDAO(PeccancyDetailDao peccancyDetailDao) {
		this.peccancyDetailDao = peccancyDetailDao;
		super.setDao(peccancyDetailDao);
	}

	@Override
	public PeccancyVo getPeccancyVo(Integer peccancyId) {
		PeccancyVo vo = new PeccancyVo();
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//根据itemId得到信息
			PeccancyDetail peccancyDetail = get(peccancyId);
			vo.setPeccancyId(peccancyId);
			if (peccancyDetail.getMarkTime()!=null) {
				vo.setFindTime(sf.format(peccancyDetail.getMarkTime()));
			}
			if (peccancyDetail.getOffender()!=null) {
				vo.setOffenderId(peccancyDetail.getOffender().getUserId());
				vo.setOffenderName(peccancyDetail.getOffender().getName());

			}
			vo.setPeccancyTypeName(peccancyDetail.getCategory().getCategoryName());
			vo.setPeccancyTypeId(peccancyDetail.getCategory().getCategoryId());
			vo.setContent(peccancyDetail.getContent());
			vo.setDutyDeptName(peccancyDetail.getDepartment().getDepartmentName());
			vo.setDutyPersonName(peccancyDetail.getDutyPersonName());
			vo.setRemark(peccancyDetail.getRemark());
			vo.setPeccancyRules(peccancyDetail.getPeccancyRules());
			vo.setMorningReport(peccancyDetail.isMorningReport());
			List<String> ruleAtaches=new ArrayList<>();
			for (int i=0;i<peccancyDetail.getAttachment().split(";").length;i++){
				String s = peccancyDetail.getAttachment().split(";")[i];
				ruleAtaches.add(s.split("\\|")[0]);
			}
			vo.setRuleAttaches(ruleAtaches);
			List<String> reformAtaches=new ArrayList<>();
			for (int i=0;i<peccancyDetail.getReformAttach().split(";").length;i++){
				String s = peccancyDetail.getReformAttach().split(";")[i];
				reformAtaches.add(s.split("\\|")[0]);
			}
			vo.setReformAttaches(reformAtaches);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;

	}

}

package com.kwchina.work.handOver.service.impl;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.core.util.string.StringUtil;
import com.kwchina.work.errorWork.enums.ErrorTypeEnum;
import com.kwchina.work.errorWork.utils.convert.Convert;
import com.kwchina.work.handOver.dao.HandOverDAO;
import com.kwchina.work.handOver.entity.HandDetail;
import com.kwchina.work.handOver.service.HandOverManager;
import com.kwchina.work.handOver.vo.HandOverVO;
import com.kwchina.work.util.EnumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service("handOverManager")
public class HandOverManagerImpl extends BasicManagerImpl<HandDetail> implements HandOverManager {

    private HandOverDAO handOverDAO;

    @Autowired
    public void setSystemErrorWorkDAO(HandOverDAO handOverDAO) {
        this.handOverDAO = handOverDAO;
        super.setDao(handOverDAO);
    }

    @Override
    public HandOverVO getVoById(Integer handId) {
        HandOverVO handOverVO = new HandOverVO();
        HandDetail handDetail = get(handId);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        handOverVO.setHandId(handId);
        if (handDetail.getTitle() != null) {
            handOverVO.setTitle(handDetail.getTitle());
        }
        if (handDetail.getContent() != null) {
            handOverVO.setContent(handDetail.getContent());
        }
        if (handDetail.getHander() != null) {
            handOverVO.setHanderId(handDetail.getHander().getUserId());
            handOverVO.setHanderName(handDetail.getHander().getName());
        }
        if (handDetail.getHandDate() != null) {
            handOverVO.setHandTime(sf.format(handDetail.getHandDate()));
        }
        List<String> attaches = new ArrayList<>();
        if (StringUtil.isNotEmpty(handDetail.getAttach())) {
            for (int i = 0; i < handDetail.getAttach().split(";").length; i++) {
                String s = handDetail.getAttach().split(";")[i];
                attaches.add(s.split("\\|")[0]);
            }
        }
        handOverVO.setAttaches(attaches);
        return handOverVO;
    }
}

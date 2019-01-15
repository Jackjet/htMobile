package com.kwchina.work.train.service.impl;

import com.kwchina.core.common.service.BasicManagerImpl;
import com.kwchina.core.util.string.StringUtil;
import com.kwchina.work.errorWork.utils.convert.Convert;
import com.kwchina.work.train.dao.TrainDAO;
import com.kwchina.work.train.entity.Train;
import com.kwchina.work.train.enums.TrainDeptEnum;
import com.kwchina.work.train.service.TrainManager;
import com.kwchina.work.train.vo.TrainVo;
import com.kwchina.work.util.EnumUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service("trainManager")
public class TrainManagerImpl extends BasicManagerImpl<Train> implements TrainManager {

    private TrainDAO trainDAO;

    @Autowired
    public void setSystemTrainCountDAO(TrainDAO trainDAO) {
        this.trainDAO = trainDAO;
        super.setDao(trainDAO);
    }

    @Override
    public TrainVo transToVo(Train train) {
        TrainVo trainVo=new TrainVo();
        trainVo.setDepartmentName(train.getDepartmentName());
        trainVo.setDepartmentNum(train.getDepartmentNum());
        trainVo.setPersonCount(train.getPersonCount());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        trainVo.setTrainTime(sdf.format(train.getTrainTime()));
        trainVo.setRecordTime(sdf.format(train.getRecordTime()));
        trainVo.setTitle(train.getTitle());
        trainVo.setTrainerName(train.getTrainerName());
        trainVo.setTrainId(train.getTrainId());
        List<String> arrAttach=new ArrayList<>();
        if(StringUtil.isNotEmpty(train.getAttach())){
            for (int i=0;i<train.getAttach().split(";").length;i++){
                String s = train.getAttach().split(";")[i];
                arrAttach.add(s.split("\\|")[0]);
            }
        }
        trainVo.setAttaches(arrAttach);
        if(train.getCategory()!=null){
            trainVo.setCategoryId(train.getCategory().getCategoryId());
            trainVo.setCategoryName(train.getCategory().getCategoryName());
        }
        if(train.getRecorder()!=null){
            trainVo.setRecorderId(train.getRecorder().getUserId());
            trainVo.setRecorderName(train.getRecorder().getName());
        }
        trainVo.setDeptName(EnumUtil.getByCode(train.getDepartmentNum(),TrainDeptEnum.class).getMsg());
        return trainVo;
    }

//    public List<Train> getYearCounts(int dataYear){
//        List<Train> list = new ArrayList<Train>();
//        String hql = "from TrainCount tcount where tcount.dataYear = " + dataYear + " order by category.categoryId,dataMonth";
//        list = getResultByQueryString(hql);
//
//        return list;
//    }
//
//
//    public Train getInstance(int dataYear, int month, int categoryId){
//        Train count = new Train();
//        String hql = " from Train t where 1=1 ";
//        if(dataYear > 0){
//            hql += " and t.dataYear = " + dataYear;
//        }
//        if(month > 0){
//            hql += " and tcount.dataMonth = " + month;
//        }
//        if(categoryId > 0){
//            hql += " and tcount.category.categoryId = " + categoryId;
//        }
//
//        hql += " order by tcount.countId desc";
//
//        List<Train> list = getResultByQueryString(hql);
//        if(list != null && list.size() > 0){
//            count = list.get(0);
//        }
//
//        return count;
//    }
}

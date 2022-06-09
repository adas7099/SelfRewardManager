package org.abhisek.rewardSystem.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.abhisek.rewardSystem.bean.PointsRequestBean;
import org.abhisek.rewardSystem.dao.DailyTimesheetBean;
import org.abhisek.rewardSystem.dao.Login;
import org.abhisek.rewardSystem.dao.PointsBean;
import org.abhisek.rewardSystem.repository.LoginRepository;
import org.abhisek.rewardSystem.repository.PointsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.gson.Gson;

@Service
@Transactional
public class PointsService {
	@Autowired
	PointsRepository pointsRepository;
	@Autowired
	Gson gson;
	@Autowired
	LoginRepository loginRepository;
	Logger logger=LoggerFactory.getLogger(PointsService.class);
	public void storePointsWhileSubmitting(DailyTimesheetBean dailyTimesheetBean,Login login) {
		PointsBean pointsBean= pointsRepository.findFirstByLoginOrderByPidDesc(login);
		PointsRequestBean pointsRequestBean =null;
		logger.info("PointsService.storePointsWhileSubmitting() pointsBean : "+gson.toJson(pointsBean));
		logger.info("PointsService.storePointsWhileSubmitting() dailyTimesheetBean : "+gson.toJson(dailyTimesheetBean));
		
		
		if(pointsBean!=null) {
			 pointsRequestBean =new PointsRequestBean(dailyTimesheetBean,pointsBean.getPoints());
		}
		else {
			 pointsRequestBean =new PointsRequestBean(dailyTimesheetBean,0);
		}
		logger.info("PointsService.storePointsWhileSubmitting() pointsRequestBean : "+gson.toJson(pointsRequestBean));
		PointsBean pointsSavingBean =new PointsBean(pointsRequestBean);
		pointsSavingBean.setLogin(login);
		logger.info("PointsService.storePointsWhileSubmitting() pointsBean : "+gson.toJson(pointsSavingBean));
		login.getPointBeans().add(pointsSavingBean);
		pointsRepository.save(pointsSavingBean);
		loginRepository.save(login);
	}
	public void deletepoints(DailyTimesheetBean dailyTimesheetBean,Login login) {
		PointsBean pointsBean=null;
		if(!CollectionUtils.isEmpty(login.getPointBeans()))
				 pointsBean= pointsRepository.findFirstByLoginOrderByPidDesc(login);
		PointsRequestBean pointsRequestBean =new PointsRequestBean();
		pointsRequestBean.setPdate(Date.valueOf(LocalDate.now()));
		pointsRequestBean.setPoints_action("reverse");
		if(pointsBean!=null)
			pointsRequestBean.setLast_points(pointsBean.getPoints());
		else
			pointsRequestBean.setLast_points(0);
			
		pointsRequestBean.setDifference((int)((-1)*dailyTimesheetBean.getPoints()));
		int points=pointsRequestBean.getLast_points()+pointsRequestBean.getDifference();
		pointsRequestBean.setPoints(points);
		pointsBean=new PointsBean(pointsRequestBean);
		pointsBean.setLogin(login);
		login.getPointBeans().add(pointsBean);
		pointsRepository.save(pointsBean);
		loginRepository.save(login);
	}
	public int redeemedPoints(int rewardValue,Login login) {
		PointsBean pointsBean= pointsRepository.findFirstByLoginOrderByPidDesc(login);
		PointsRequestBean pointsRequestBean =new PointsRequestBean();
		pointsRequestBean.setPdate(Date.valueOf(LocalDate.now()));
		pointsRequestBean.setPoints_action("redeem");
		if(null!=pointsBean) {
			pointsRequestBean.setLast_points(pointsBean.getPoints());
		}
		else {
			pointsRequestBean.setLast_points(0);
		}
		pointsRequestBean.setDifference((-1)*rewardValue);
		int points=pointsRequestBean.getLast_points()+pointsRequestBean.getDifference();
		pointsRequestBean.setPoints(points);
		pointsBean=new PointsBean(pointsRequestBean);
		pointsBean.setLogin(login);
		
		login.getPointBeans().add(pointsBean);
		loginRepository.save(login);
		pointsRepository.save(pointsBean);
		return points;
	}
	public int getPoints(Login login) {
		int points=0;
		if(pointsRepository.findFirstByLoginOrderByPidDesc(login)!=null)
			points=pointsRepository.findFirstByLoginOrderByPidDesc(login).getPoints();
		return points;
	}
	public List<PointsBean> getLast10PointsBean(Login login){
		return pointsRepository.getPointsBeanLast10Records(login.getUserid());
	}
}

package org.abhisek.rewardSystem.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.abhisek.rewardSystem.bean.DailyTimesheetRequestBean;
import org.abhisek.rewardSystem.bean.DailyTimesheetSubmissionBean;
import org.abhisek.rewardSystem.bean.PointsRequestBean;
import org.abhisek.rewardSystem.dao.DailyTimesheetBean;
import org.abhisek.rewardSystem.dao.Login;
import org.abhisek.rewardSystem.dao.PointsBean;
import org.abhisek.rewardSystem.repository.DailyTimesheetRepository;
import org.abhisek.rewardSystem.repository.LoginRepository;
import org.abhisek.rewardSystem.repository.PointsRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import org.slf4j.Logger;

@Service
@Transactional
public class DailyTimesheetService {

	
	Logger logger=LoggerFactory.getLogger(DailyTimesheetService.class);
	@Autowired
	DailyTimesheetRepository dailyTimesheetRepository;
	@Autowired
	PointsRepository pointsRepository;
	@Autowired
	LoginRepository loginRepository;
	
	public List<DailyTimesheetBean> getAllTimesheets(Login login) {
		return dailyTimesheetRepository.findAllByLogin(login);
	}
	public DailyTimesheetBean createTimeSheet(DailyTimesheetRequestBean dailyTimesheetRequestBean,Login login) {
		logger.info("dailyTimesheetRequestBean" +(new Gson().toJson(dailyTimesheetRequestBean)));
		DailyTimesheetBean dailyTimesheetBean=new DailyTimesheetBean(dailyTimesheetRequestBean);
		logger.info("DailyTimesheetBean" +(new Gson().toJson(dailyTimesheetBean)));
		
		dailyTimesheetBean.setLogin(login);
		login.getTimesheets().add(dailyTimesheetBean);
		loginRepository.save(login);
		dailyTimesheetRepository.save(dailyTimesheetBean);
		//logger.info("DailyTimesheetBean" +(new Gson().toJson(dailyTimesheetBean)));
		return new DailyTimesheetBean(dailyTimesheetRequestBean);
	}
	public List<DailyTimesheetSubmissionBean> getlast10Timesheets(Login login){
		List<DailyTimesheetSubmissionBean> dailyTimesheetSubmissionBeans=new ArrayList<DailyTimesheetSubmissionBean>();
		for(DailyTimesheetBean dailyTimesheetBean:
			dailyTimesheetRepository.getDailyTimesheetBeanLast10Records(login.getUserid())) {
			dailyTimesheetSubmissionBeans.add(new DailyTimesheetSubmissionBean(dailyTimesheetBean));
			logger.info("DailyTimesheetBean" +(new Gson().toJson(dailyTimesheetBean)));
		}
		logger.info("DailyTimesheetBean" +(new Gson().toJson(dailyTimesheetSubmissionBeans)));
		return dailyTimesheetSubmissionBeans;
	}
	
	public long getCountByTdate(Date tdate, Login login) {
		
		return dailyTimesheetRepository.countByTdateAndLogin(tdate, login);
	}
	
	public DailyTimesheetBean findByTdate(Date tdate, Login login) {
		return dailyTimesheetRepository.findByTdateAndLogin(tdate, login).get(0);
								
	}
	public void deleteByTdateAndLogin(Date tdate,Login login) {
		
			dailyTimesheetRepository.deleteByTdateAndLogin(tdate,login);
		
		
	}
	
}

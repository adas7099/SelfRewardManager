package org.abhisek.rewardSystem.bean;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import org.abhisek.rewardSystem.dao.DailyTimesheetBean;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyTimesheetSubmissionBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7357446490278425663L;
	private Date tdate;
	private List<TimeSheetTask> timeSheetTasks;
	private double points;
	
	@SuppressWarnings("unchecked")
	public DailyTimesheetSubmissionBean(DailyTimesheetBean dailyTimesheetBean) {
		this.tdate=dailyTimesheetBean.getTdate();
		this.timeSheetTasks=new Gson().fromJson(dailyTimesheetBean.getTimesheetString(),List.class);
		this.points=dailyTimesheetBean.getPoints();
	}
}

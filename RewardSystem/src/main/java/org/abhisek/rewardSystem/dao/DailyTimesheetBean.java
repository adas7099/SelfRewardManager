package org.abhisek.rewardSystem.dao;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.abhisek.rewardSystem.bean.DailyTimesheetRequestBean;
import org.abhisek.rewardSystem.bean.TimeSheetTask;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name="DAILY_TIMESHEET")
public class DailyTimesheetBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="tid")
	private int tid;
	@Column(name="tdate")
	private Date tdate;
	@Column(name="timesheetString")
	private String timesheetString;
	@Column(name="points")
	private double points;
	@Expose(serialize = false)
	@ManyToOne
	private Login login;
	
	public DailyTimesheetBean(DailyTimesheetRequestBean dailyTimesheetRequestBean) {
		this.tdate=dailyTimesheetRequestBean.getTdate();
		this.timesheetString=new Gson().toJson(dailyTimesheetRequestBean.getTimeSheetTasks());
		int total=0;
		for(TimeSheetTask timeSheetTask:dailyTimesheetRequestBean.getTimeSheetTasks()) {
			if(timeSheetTask.getTaskType().equalsIgnoreCase("Boolean")) {
				total=(timeSheetTask.isBoolPoint()?1:0)*timeSheetTask.getPointvalue();
			}
			if(timeSheetTask.getTaskType().equalsIgnoreCase("Number")) {
				total=timeSheetTask.getPointvalue()*timeSheetTask.getEachPoint();
			}
		}
		this.points=total;
		
	}

	
}

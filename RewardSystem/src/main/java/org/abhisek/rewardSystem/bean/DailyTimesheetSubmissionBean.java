package org.abhisek.rewardSystem.bean;

import java.io.Serializable;
import java.sql.Date;

import org.abhisek.rewardSystem.dao.DailyTimesheetBean;

import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class DailyTimesheetSubmissionBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7357446490278425663L;
	private int tid;
	private Date tdate;
	private int noOfPomo;
	private int noOfminsWatchVideo;
	private Boolean exercise;
	private int noOfAptiPomo;
	private int noOfQuestions;
	private double points;
	
	public DailyTimesheetSubmissionBean(DailyTimesheetBean dailyTimesheetBean) {
		this.tid=dailyTimesheetBean.getTid();
		this.tdate=dailyTimesheetBean.getTdate();
		this.noOfPomo=dailyTimesheetBean.getNoOfPomo();
		this.noOfAptiPomo=dailyTimesheetBean.getNoOfAptiPomo();
		this.noOfminsWatchVideo=dailyTimesheetBean.getNoOfWatchVideo();
		this.exercise=dailyTimesheetBean.getExercise();
		this.noOfAptiPomo=dailyTimesheetBean.getNoOfAptiPomo();
		this.noOfQuestions=dailyTimesheetBean.getNoOfQuestions();
		this.points=dailyTimesheetBean.getPoints();
	}
}

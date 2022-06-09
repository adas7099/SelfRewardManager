package org.abhisek.rewardSystem.dao;

import java.sql.Date;

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
	@Column(name="noofpomo")
	private int noOfPomo;
	@Column(name="noofwatchvideo")
	private int noOfWatchVideo;
	@Column(name="Exercise")
	private Boolean exercise;
	@Column(name="noofaptipomo")
	private int noOfAptiPomo;
	@Column(name="noofquestions")
	private int noOfQuestions;
	@Column(name="points")
	private double points;
	@Expose(serialize = false)
	@ManyToOne
	private Login login;
	
	public DailyTimesheetBean(DailyTimesheetRequestBean dailyTimesheetRequestBean) {
		this.tdate=dailyTimesheetRequestBean.getTdate();
		this.noOfPomo=dailyTimesheetRequestBean.getNoOfPomo();
		this.noOfAptiPomo=dailyTimesheetRequestBean.getNoOfAptiPomo();
		this.noOfWatchVideo=dailyTimesheetRequestBean.getNoOfminsWatchVideo();
		this.exercise=dailyTimesheetRequestBean.getExercise();
		this.noOfAptiPomo=dailyTimesheetRequestBean.getNoOfAptiPomo();
		this.noOfQuestions=dailyTimesheetRequestBean.getNoOfQuestions();
		this.points=noOfAptiPomo+(noOfWatchVideo*0.1)+noOfAptiPomo+(noOfQuestions*0.1)+(exercise?1:0);
	}

	
}

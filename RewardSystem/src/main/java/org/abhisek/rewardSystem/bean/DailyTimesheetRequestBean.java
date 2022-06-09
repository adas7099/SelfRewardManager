package org.abhisek.rewardSystem.bean;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter 
public class DailyTimesheetRequestBean {
	
	private Date tdate;
	private int noOfPomo;
	private int NoOfminsWatchVideo;
	private Boolean exercise;
	private int noOfAptiPomo;
	private int noOfQuestions;
	private double points;
}

package org.abhisek.rewardSystem.bean;

import java.sql.Date;

import org.abhisek.rewardSystem.dao.DailyTimesheetBean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter @NoArgsConstructor
public class PointsRequestBean {
	private Date pdate;
	private int last_points;
	private String points_action;
	private int difference;
	private int points;
	public PointsRequestBean(DailyTimesheetBean dailyTimesheetBean,int last_points) {
		this.pdate=dailyTimesheetBean.getTdate();
		this.last_points=last_points;
		if(dailyTimesheetBean.getPoints()>=0)
			this.points_action="credited";
		else
			this.points_action="debited";
		this.difference=(int)dailyTimesheetBean.getPoints();
		this.points=last_points+this.difference;
	}
}

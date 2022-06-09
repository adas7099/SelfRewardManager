package org.abhisek.rewardSystem.dao;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.abhisek.rewardSystem.bean.PointsRequestBean;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name="total_points")
public class PointsBean {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="pid")
	private int pid;
	@Column(name="pdate")
	private Date pdate;
	@Column(name="last_points")
	private int last_points;
	@Column(name="point_action")
	private String point_action;
	@Column(name="difference")
	private int difference;
	@Column(name="points")
	private int points;
	@Expose(serialize = false)
	@ManyToOne
	private Login login;
	
	public PointsBean(PointsRequestBean pointsRequestBean) {
		this.pdate=pointsRequestBean.getPdate();
		this.last_points=pointsRequestBean.getLast_points();
		this.point_action=pointsRequestBean.getPoints_action();
		this.difference=pointsRequestBean.getDifference();
		this.last_points=pointsRequestBean.getLast_points();
		this.points=pointsRequestBean.getPoints();
	}
}

package org.abhisek.rewardSystem.dao;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.abhisek.rewardSystem.bean.RewardRequestBean;

import com.google.gson.annotations.Expose;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="Rewards")
@Getter @Setter
@NoArgsConstructor
@Table(name="Rewards")
public class RewardsBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rewardid")
	private int rewardid;
	@Column(name="rewardcreateddate")
	private Date rewardcreatedDate;
	@Column(name="rewardname")
	private String rewardName;
	@Column(name="rewardvalue")
	private int rewardvalue;
	@Column(name="redeemed")
	private boolean redeemed;
	@Column(name="permanent")
	private boolean permanent;
	@Column(name="redeemeddate")
	private Date redeemedDate;
	@Expose(serialize = false)
	@ManyToOne
	private Login login;
	public RewardsBean(RewardRequestBean rewardRequestBean) {
		rewardcreatedDate=rewardRequestBean.getRewardcreatedDate();
		rewardName=rewardRequestBean.getRewardName();
		rewardvalue=rewardRequestBean.getRewardvalue();
		redeemed=rewardRequestBean.isRedeemed();
		permanent=rewardRequestBean.isPermanent();
		redeemedDate=rewardRequestBean.getRedeemedDate();
	}
}

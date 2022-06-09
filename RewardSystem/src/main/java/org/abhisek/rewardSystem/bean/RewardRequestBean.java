package org.abhisek.rewardSystem.bean;

import java.io.Serializable;
import java.sql.Date;


import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class RewardRequestBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date rewardcreatedDate;
	private String rewardName;
	private int rewardvalue;
	private boolean redeemed;
	private boolean permanent;
	private Date redeemedDate;
}

package org.abhisek.rewardSystem.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @AllArgsConstructor @NoArgsConstructor
public class RedeemRewardRequest {
	private int rewardid;
	private String rewardName;
	private int rewardvalue;
}

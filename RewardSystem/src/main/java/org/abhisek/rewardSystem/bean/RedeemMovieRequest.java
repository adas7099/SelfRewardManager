package org.abhisek.rewardSystem.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RedeemMovieRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2769414192334892949L;
	private String ottOption;
	private String movieName;

}

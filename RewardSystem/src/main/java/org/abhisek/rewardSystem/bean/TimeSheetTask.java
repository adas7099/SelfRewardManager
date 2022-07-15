package org.abhisek.rewardSystem.bean;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TimeSheetTask implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String taskName;
	private String taskType;
	private int eachPoint;
	private boolean boolPoint;
	private int pointvalue;
}

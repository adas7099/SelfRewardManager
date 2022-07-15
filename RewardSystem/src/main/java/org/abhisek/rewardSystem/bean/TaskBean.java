package org.abhisek.rewardSystem.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class TaskBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8982591190060153349L;
	private String taskName;
	private String taskType;
	private int pointValue;
	
	public TaskBean(String taskName, String taskType, int pointValue) {
		super();
		this.taskName = taskName;
		this.taskType = taskType;
		this.pointValue = pointValue;
	}
	
}

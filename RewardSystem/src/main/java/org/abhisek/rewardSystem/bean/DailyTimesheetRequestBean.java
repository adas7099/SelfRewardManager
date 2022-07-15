package org.abhisek.rewardSystem.bean;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter 
@NoArgsConstructor
@AllArgsConstructor
public class DailyTimesheetRequestBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date tdate;
	private List<TimeSheetTask> timeSheetTasks;
	private double points;
	public DailyTimesheetRequestBean(TaskBeanList taskBeanList) {
		if(null==this.timeSheetTasks)
			this.timeSheetTasks=new ArrayList<TimeSheetTask>();
		if(taskBeanList!=null && !CollectionUtils.isEmpty(taskBeanList.getTaskBeans())) {
			for(TaskBean taskBean:taskBeanList.getTaskBeans()) {
				TimeSheetTask timeSheetTask=new TimeSheetTask();
				timeSheetTask.setTaskName(taskBean.getTaskName());
				timeSheetTask.setTaskType(taskBean.getTaskType());
				timeSheetTask.setPointvalue(taskBean.getPointValue());
				this.timeSheetTasks.add(timeSheetTask);
			}
		}
	}
	
}

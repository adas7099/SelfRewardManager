package org.abhisek.rewardSystem.service;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.abhisek.rewardSystem.Exception.TaskException;
import org.abhisek.rewardSystem.bean.TaskBean;
import org.abhisek.rewardSystem.bean.TaskBeanList;
import org.abhisek.rewardSystem.dao.Login;
import org.abhisek.rewardSystem.dao.Task;
import org.abhisek.rewardSystem.repository.LoginRepository;
import org.abhisek.rewardSystem.repository.TaskRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.gson.Gson;
@Service
@Transactional
public class TaskService {
	Logger logger = LoggerFactory.getLogger(TaskService.class);
	@Autowired
	TaskRepository taskRepository;
	@Autowired
	LoginRepository loginRepository;
	@Autowired
	Gson gson;
	
	public TaskBeanList getTaskList(Login login) {
		String taskJson=null;
		TaskBeanList tasks=null;
		Task task=taskRepository.findFirstByLogin(login);
		if(task!=null)
			taskJson=task.getTaskString();
		if(!StringUtils.isEmpty(taskJson)) {
			tasks=gson.fromJson(taskJson, TaskBeanList.class);
		}
		return tasks;
	}
	public TaskBeanList addTaskBean(TaskBean taskBean,Login login) throws TaskException {
		Task task=taskRepository.findFirstByLogin(login);
		String taskJson=null;
		if(task!=null) {			
			taskJson=task.getTaskString();
			TaskBeanList tasks=null;
			if(!StringUtils.isEmpty(taskJson)) {
				tasks=gson.fromJson(taskJson, TaskBeanList.class);
			}
			if(tasks==null || CollectionUtils.isEmpty(tasks.getTaskBeans())) {
				tasks=new TaskBeanList();
				tasks.setTaskBeans(new ArrayList<TaskBean>());
			}
			tasks.getTaskBeans().add(taskBean); 
			taskJson=gson.toJson(tasks,TaskBeanList.class);
			logger.info("taskJson"+taskJson);
			logger.info("count By TaskName"+countByTaskName(tasks, taskBean.getTaskName()));
			if(countByTaskName(tasks, taskBean.getTaskName())<=1) {
				taskRepository.updateTaskStringByTaskid(taskJson, task.getTaskid());
				return tasks;
			}
			else {
				throw new TaskException("TaskName is Already present");
			}
			
		}
		else {
			task=new Task();
			TaskBeanList tasks=new TaskBeanList();
			tasks.setTaskBeans(new ArrayList<TaskBean>());
			tasks.getTaskBeans().add(taskBean);
			taskJson=gson.toJson(tasks,TaskBeanList.class);
			task.setTaskString(taskJson);
			task.setLogin(login);
			login.setTask(task);
			taskRepository.save(task);
			loginRepository.save(login);
			return tasks;
		}
	}
	public TaskBeanList removeTaskBean(String taskName,Login login) {
		Task task=taskRepository.findFirstByLogin(login);
		String taskJson=null;
		if(task!=null) {			
			taskJson=task.getTaskString();
			TaskBeanList tasks=null;
			if(!StringUtils.isEmpty(taskJson)) {
				tasks=gson.fromJson(taskJson, TaskBeanList.class);
			}
			boolean isChanged=false;
			if(countByTaskName(tasks, taskName)!=0) {
				for(TaskBean taskBean:tasks.getTaskBeans()) {
					if(taskBean.getTaskName().equalsIgnoreCase(taskName)) {
						tasks.getTaskBeans().remove(taskBean);
						isChanged=true;
						break;
					}
				}
				taskJson=gson.toJson(tasks,TaskBeanList.class);
				logger.info("taskJson"+taskJson);	
				if(isChanged)
					taskRepository.updateTaskStringByTaskid(taskJson, task.getTaskid());

			}
			return tasks;
		}
		return null;
	}
	public long countByTaskName(TaskBeanList tasks,String taskName) {
		if(null!=tasks &&!CollectionUtils.isEmpty(tasks.getTaskBeans()))
			return tasks.getTaskBeans().stream().filter(task->task.getTaskName().equalsIgnoreCase(taskName)).count();
		else
			return 0;
	}
}

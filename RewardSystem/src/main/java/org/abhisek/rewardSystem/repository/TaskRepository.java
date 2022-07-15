package org.abhisek.rewardSystem.repository;

import org.abhisek.rewardSystem.dao.Login;
import org.abhisek.rewardSystem.dao.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface TaskRepository  extends JpaRepository<Task, Integer>{
	public Task findFirstByLogin(Login login);
	@Modifying
	@Query(value="update Task t set t.task_string=:taskString where t.taskid=:taskid",nativeQuery = true)
	public void updateTaskStringByTaskid(String taskString,int taskid);
}

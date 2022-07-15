package org.abhisek.rewardSystem.repository;

import java.sql.Date;
import java.util.List;

import org.abhisek.rewardSystem.dao.DailyTimesheetBean;
import org.abhisek.rewardSystem.dao.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface DailyTimesheetRepository extends JpaRepository<DailyTimesheetBean, Integer>{
	
	@Query(nativeQuery = true,value="Select * from DAILY_TIMESHEET d Where d.login_userid=:userid order by tid desc limit 10")
	public List<DailyTimesheetBean> getDailyTimesheetBeanLast10Records(@Param("userid")int userid);
	
	public long countByTdateAndLogin(Date tdate,Login login);
	
	public long deleteByTdateAndLogin(Date tdate,Login login);

	public List<DailyTimesheetBean> findByTdateAndLogin(Date tdate,Login login);

	public long deleteByTidAndLogin(int tid,Login login);
	
	public List<DailyTimesheetBean>  findAllByLogin(Login login);
}

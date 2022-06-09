package org.abhisek.rewardSystem.repository;

import java.util.List;

import org.abhisek.rewardSystem.dao.Login;
import org.abhisek.rewardSystem.dao.PointsBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface PointsRepository extends JpaRepository<PointsBean, Integer>{
	PointsBean findFirstByLoginOrderByPidDesc(Login login);
	@Query(nativeQuery = true,value="Select * from total_points t where t.login_userid=:userid order by pid desc limit 10")
	public List<PointsBean> getPointsBeanLast10Records(int userid);
}

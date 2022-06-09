package org.abhisek.rewardSystem.repository;


import java.sql.Date;
import java.util.List;

import org.abhisek.rewardSystem.dao.Login;
import org.abhisek.rewardSystem.dao.RewardsBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface RewardsRepository extends JpaRepository<RewardsBean, Integer>{
	
	public List<RewardsBean> findByLoginAndPermanentOrRedeemed(Login login,boolean permanent,boolean redeemed);
	public long deleteByRewardid(int rewardid);
	@Modifying
	@Query(value="update Rewards r set r.redeemed=true where r.rewardid=:rewardid",nativeQuery = true)
	public void makeRedeemedTrueByRewardid(@Param("rewardid") int rewardid);
	@Modifying
	@Query(value="update Rewards r set r.redeemedDate=:redeemedDate where r.rewardid=:rewardid")
	public void setRedeemedDateByRewardid(@Param("redeemedDate") Date redeemedDate,@Param("rewardid") int rewardid);

	public List<RewardsBean> findByRewardid(int rewardid);
	
	
}

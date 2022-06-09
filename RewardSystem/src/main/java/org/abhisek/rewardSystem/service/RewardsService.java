package org.abhisek.rewardSystem.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.abhisek.rewardSystem.bean.RewardRequestBean;
import org.abhisek.rewardSystem.dao.Login;
import org.abhisek.rewardSystem.dao.RewardsBean;
import org.abhisek.rewardSystem.repository.LoginRepository;
import org.abhisek.rewardSystem.repository.RewardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@Transactional
public class RewardsService {
	@Autowired
	RewardsRepository rewardsRepository;
	@Autowired
	LoginRepository loginRepository;
	
	public void createrewards(RewardRequestBean rewardRequestBean, Login login) {
		RewardsBean rewardsBean= new RewardsBean(rewardRequestBean);
		rewardsBean.setRewardcreatedDate(Date.valueOf(LocalDate.now()));
		rewardsBean.setLogin(login);
		//rewardsRepository.save(rewardsBean);
		login.getRewardsBeans().add(rewardsBean);
		loginRepository.save(login);
	}
	public List<RewardsBean> findeligibleRewards(Login login){
		return rewardsRepository.findByLoginAndPermanentOrRedeemed(login,true, false);
	}
	public void deletereward(int rewardid) {
		rewardsRepository.deleteByRewardid(rewardid);
	}
	public RewardsBean findByRewardid(int rewardid) {
		List<RewardsBean> rewardsBeans=rewardsRepository.findByRewardid(rewardid);
		if(null!=rewardsBeans && !rewardsBeans.isEmpty())
			return rewardsBeans.get(0);
		else
			return null;
	}
	public void redeemReward(int rewardid) {
		rewardsRepository.makeRedeemedTrueByRewardid(rewardid);
		rewardsRepository.setRedeemedDateByRewardid(Date.valueOf(LocalDate.now()), rewardid);
		
	}
}

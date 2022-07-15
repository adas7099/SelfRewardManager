package org.abhisek.rewardSystem.verify;

import org.abhisek.rewardSystem.bean.DailyTimesheetRequestBean;
import org.abhisek.rewardSystem.bean.DeleteDateBean;
import org.abhisek.rewardSystem.bean.RedeemMovieId;
import org.abhisek.rewardSystem.bean.RedeemWebseriesId;
import org.abhisek.rewardSystem.dao.Movies;
import org.abhisek.rewardSystem.dao.RewardsBean;
import org.abhisek.rewardSystem.dao.Webseries;

public class VerifyObjects {
	
	public static boolean verifydailyTimesheetRequestBean(DailyTimesheetRequestBean dailyTimesheetRequestBean) {
		if(dailyTimesheetRequestBean==null || dailyTimesheetRequestBean.getTdate()==null ||dailyTimesheetRequestBean.getTimeSheetTasks()==null) {
			return false;
		}
		return true;
	}
	public static boolean verifyDeleteDateBean(DeleteDateBean deleteDateBean) {
		if(deleteDateBean==null || deleteDateBean.getDdate()==null) {
			return false;
		}
		return true;
	}
	public static boolean verifyRewardsBean(RewardsBean rewardsBean) {
		if(rewardsBean==null || rewardsBean.getRewardName()==null ||rewardsBean.getRewardvalue()==0
				||(!rewardsBean.isPermanent() && rewardsBean.isRedeemed())) {
			return false;
		}
		return true;
	}
	public static boolean verifyRewardsBeanForDelete(RewardsBean rewardsBean) {
		if(rewardsBean==null || rewardsBean.getRewardName()==null ) {
			return false;
		}
		return true;
	}
	public static boolean verifyRedeemMovieId(RedeemMovieId redeemMovieId) {
		if(redeemMovieId==null || redeemMovieId.getMovieId()==null ||redeemMovieId.getOttOption()==null
				||redeemMovieId.getOttOption()==null) {
			return false;
		}
		return true;
	}
	public static boolean verifyRedeemWebseriesId(RedeemWebseriesId redeemWebseriesId) {
		if(redeemWebseriesId==null || redeemWebseriesId.getWebseriesId()==null 
				|| redeemWebseriesId.getWebseriesName()==null||redeemWebseriesId.getOttOption()==null) {
			return false;
		}
		return true;
	}
	public static boolean verifyMovies(Movies movies) {
		if(movies==null||movies.getMovieName()==null || movies.getOtt()==null) {
			return false;
		}
		return true;
	}
	public static boolean verifyWebSeries(Webseries webseries) {
		if(webseries==null || webseries.getWebseriesname()==null
				||webseries.getOtt()==null) {
			return false;
		}
		return true;
	}
}

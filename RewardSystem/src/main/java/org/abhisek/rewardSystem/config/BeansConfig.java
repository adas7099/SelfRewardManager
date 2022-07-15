package org.abhisek.rewardSystem.config;

import org.abhisek.rewardSystem.bean.DailyTimesheetRequestBean;
import org.abhisek.rewardSystem.bean.DeleteDateBean;
import org.abhisek.rewardSystem.bean.RedeemMovieId;
import org.abhisek.rewardSystem.bean.RedeemRewardRequest;
import org.abhisek.rewardSystem.bean.RedeemWebseriesId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Configuration
public class BeansConfig {
	@Bean(name="gson")
	public Gson getGson() {
		return new Gson();
	}
	@Bean(name="logGson")
	public Gson getLogGson() {
		return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	}
	@Bean(name="dailyTimesheetRequestBean")
	public DailyTimesheetRequestBean getDailyTimesheetRequestBean(){
		return new DailyTimesheetRequestBean();
	}
	@Bean(name="deleteDateBean")
	public DeleteDateBean getdeleteDateBean() {
		return new DeleteDateBean();
	}
	@Bean(name="redeemRewardRequest")
	public RedeemRewardRequest getRedeemRewardRequest() {
		return new RedeemRewardRequest();
		
	}
	@Bean(name="redeemMovieId")
	public RedeemMovieId getRedeemMovieId() {
		return new RedeemMovieId();
	}
	@Bean(name="redeemWebSeriesId")
	public RedeemWebseriesId getRedeemWebSeriesId() {
		return new RedeemWebseriesId();
	}
	
}

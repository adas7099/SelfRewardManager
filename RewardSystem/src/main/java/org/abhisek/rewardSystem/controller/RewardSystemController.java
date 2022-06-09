package org.abhisek.rewardSystem.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.abhisek.rewardSystem.bean.DailyTimesheetRequestBean;
import org.abhisek.rewardSystem.bean.DailyTimesheetSubmissionBean;
import org.abhisek.rewardSystem.bean.DeleteDateBean;
import org.abhisek.rewardSystem.bean.MessageBean;
import org.abhisek.rewardSystem.bean.RedeemMovieId;
import org.abhisek.rewardSystem.bean.RedeemMovieRequest;
import org.abhisek.rewardSystem.bean.RedeemRewardRequest;
import org.abhisek.rewardSystem.bean.RedeemWebseriesId;
import org.abhisek.rewardSystem.bean.RedeemWebseriesRequest;
import org.abhisek.rewardSystem.bean.RewardRequestBean;
import org.abhisek.rewardSystem.dao.DailyTimesheetBean;
import org.abhisek.rewardSystem.dao.Login;
import org.abhisek.rewardSystem.dao.Movies;
import org.abhisek.rewardSystem.dao.PointsBean;
import org.abhisek.rewardSystem.dao.RewardsBean;
import org.abhisek.rewardSystem.dao.Webseries;
import org.abhisek.rewardSystem.service.DailyTimesheetService;
import org.abhisek.rewardSystem.service.LoginService;
import org.abhisek.rewardSystem.service.MoviesService;
import org.abhisek.rewardSystem.service.PointsService;
import org.abhisek.rewardSystem.service.RewardsService;
import org.abhisek.rewardSystem.service.WebseriesService;
import org.abhisek.rewardSystem.verify.VerifyObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;

@Controller
@RequestMapping("/user")
public class RewardSystemController {
	Logger logger = LoggerFactory.getLogger(DailyTimesheetService.class);
	@Autowired
	Gson gson;
	@Autowired
	DailyTimesheetService dailyTimesheetService;
	@Autowired
	PointsService pointsService;
	@Autowired
	RewardsService rewardsService;
	@Autowired
	MoviesService moviesService;
	@Autowired
	WebseriesService WebseriesService;
	@Autowired
	DailyTimesheetRequestBean dailyTimesheetRequestBean;
	@Autowired
	DeleteDateBean deleteDateBean;
	@Autowired
	RedeemRewardRequest redeemRewardRequest;
	@Autowired
	RedeemMovieId redeemMovieId;
	@Autowired
	RedeemWebseriesId redeemWebseriesId;
	@Autowired
	LoginService loginService;
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home( Model model,HttpSession session,Principal principal) {
		//logger.info("userName:"+principal.getName());
		Login login=loginService.getLoginByEmailOrUserName(principal.getName());
		model.addAttribute("Points", pointsService.getPoints(login));
		return "home";
	}

	@RequestMapping(value = "/submitTimesheet", method = RequestMethod.POST)
	public String submitTimesheet(Model model,
			@ModelAttribute("dailyTimesheetRequestBean") DailyTimesheetRequestBean dailyTimesheetRequestBean
			,Principal principal) {
		Login login=loginService.getLoginByEmailOrUserName(principal.getName());
		long count=dailyTimesheetService.getCountByTdate(dailyTimesheetRequestBean.getTdate(),login);
		
		logger.info("count :"+count);
		if(count==0) {
			this.dailyTimesheetRequestBean=dailyTimesheetRequestBean;
			model.addAttribute("dailyTimesheetRequestBean", dailyTimesheetRequestBean);
			return "pointssurity";
		}
		else {
			MessageBean messageBean = new MessageBean("Error", "Date "+dailyTimesheetRequestBean.getTdate()+" Already there");
			model.addAttribute("messageBean", messageBean);
		}
		return "sucesssubmit";
	}

	@RequestMapping(value = "/suresubmittimesheet", method = RequestMethod.GET)
	public String suresubmitTimesheet(Model model,Principal principal) {
		try {
			if(VerifyObjects.verifydailyTimesheetRequestBean(dailyTimesheetRequestBean)) {
				Login login=loginService.getLoginByEmailOrUserName(principal.getName());
				long count=dailyTimesheetService.getCountByTdate(dailyTimesheetRequestBean.getTdate(),login);
				logger.info("count :"+count);
				if(count==0) {
					DailyTimesheetBean dailyTimesheetBean = dailyTimesheetService.createTimeSheet(this.dailyTimesheetRequestBean,login);
					dailyTimesheetRequestBean.setPoints(dailyTimesheetBean.getPoints());
					model.addAttribute("dailyTimesheetRequestBean", dailyTimesheetRequestBean);
					
					pointsService.storePointsWhileSubmitting(dailyTimesheetBean,login);
					logger.info("Sucessfully submitted");
					MessageBean messageBean = new MessageBean("INFO", "Sucessfully Inserted");
					model.addAttribute("messageBean", messageBean);
				}
				else {
					MessageBean messageBean = new MessageBean("Error", "Date "+dailyTimesheetRequestBean.getTdate()+" Already there");
					model.addAttribute("messageBean", messageBean);
				}
			}
			else {
				MessageBean messageBean = new MessageBean("ERROR", "dailyTimesheetRequestBean is null");
				model.addAttribute("messageBean", messageBean);
			}
		}
		catch (Exception e) {
			MessageBean messageBean = new MessageBean("ERROR", "error occured ");
			e.printStackTrace();
			model.addAttribute("messageBean", messageBean);
			
			
		}
		return "sucesssubmit";
	}
	@RequestMapping(value = "/submit", method = RequestMethod.GET)
	public String submit(Model model) {
		DailyTimesheetRequestBean dailyTimesheetRequestBean = new DailyTimesheetRequestBean();
		model.addAttribute("dailyTimesheetRequestBean", dailyTimesheetRequestBean);
		logger.info("submit called");
		return "submit";
	}

	@RequestMapping(value = "/submissionrecords", method = RequestMethod.GET)
	public String submissionrecords(Model model,Principal principal) {
		Login login=loginService.getLoginByEmailOrUserName(principal.getName());
		List<DailyTimesheetSubmissionBean> dailyTimesheetSubmissionBeans = dailyTimesheetService.getlast10Timesheets(login);
		model.addAttribute("dailyTimesheetRequestBeans", dailyTimesheetSubmissionBeans);
		return "submissionrecords";
	}
	@RequestMapping(value = "/deletetimesheet", method = RequestMethod.GET)
	public String deletetimesheet(Model model) {
		DeleteDateBean deleteDateBean= new DeleteDateBean();
		model.addAttribute("deleteDateBean", deleteDateBean);
		return "deletetimesheet";
	}
	
	@RequestMapping(value = "/deletetimesheetaction", method = RequestMethod.POST)
	public String deletetimesheetaction(@ModelAttribute DeleteDateBean deleteDateBean,Model model,Principal principal) {
		logger.info("Date :"+new Gson().toJson(deleteDateBean));
		Login login=loginService.getLoginByEmailOrUserName(principal.getName());
		long count=dailyTimesheetService.getCountByTdate(deleteDateBean.getDdate(),login);
		if(count==0) {
			MessageBean messageBean = new MessageBean("ERROR", "Timesheet is not present for "+deleteDateBean.getDdate());
			model.addAttribute("messageBean", messageBean);
		}
		else {
			this.deleteDateBean=deleteDateBean;
			model.addAttribute("deleteDateBean", deleteDateBean);
			return "deletetimesheetsurity";
		}
		return "sucesssubmit";
	}
	@RequestMapping(value = "/suritydeletetimesheet", method = RequestMethod.GET)
	public String sureDeletetimesheetaction(Model model,Principal principal) {
		try {
			if(VerifyObjects.verifyDeleteDateBean(deleteDateBean)) {
				Login login=loginService.getLoginByEmailOrUserName(principal.getName());
				DailyTimesheetBean dailyTimesheetBean=dailyTimesheetService.findByTdate(deleteDateBean.getDdate(),login);
				pointsService.deletepoints(dailyTimesheetBean,login);
				dailyTimesheetService.deleteByTdateAndLogin(deleteDateBean.getDdate(),login);
				MessageBean messageBean = new MessageBean("INFO", "Timesheet is deleted For "+deleteDateBean.getDdate());
				model.addAttribute("messageBean", messageBean);
			}
			else {
				MessageBean messageBean = new MessageBean("ERROR", "deleteDateBean is null");
				model.addAttribute("messageBean", messageBean);
			}
		}
		catch (Exception e) {
			MessageBean messageBean = new MessageBean("ERROR", "error occured ");
			e.printStackTrace();
			model.addAttribute("messageBean", messageBean);
		}
		return "sucesssubmit";
	}
	@RequestMapping(value = "/addreward", method = RequestMethod.GET)
	public String addreward(Model model) {
		RewardRequestBean rewardRequestBean= new RewardRequestBean();
		model.addAttribute("rewardRequestBean", rewardRequestBean);
		return "addreward";
	}
	@RequestMapping(value = "/addrewardsubmission", method = RequestMethod.POST)
	public String addrewardsubmission(Model model,@ModelAttribute RewardRequestBean rewardRequestBean,Principal principal) {
		Login login=loginService.getLoginByEmailOrUserName(principal.getName());
		rewardsService.createrewards(rewardRequestBean,login);
		MessageBean messageBean = new MessageBean("INFO", "Reward added");
		model.addAttribute("messageBean", messageBean);
		return "sucesssubmit";
	}
	@RequestMapping(value = "/redeemReward", method = RequestMethod.GET)
	public String redeemReward(Model model,Principal principal) {
		Login login=loginService.getLoginByEmailOrUserName(principal.getName());
		List<RewardsBean> redeemeligibleRecords= rewardsService.findeligibleRewards(login);
		model.addAttribute("redeemeligibleRecords", redeemeligibleRecords);
		return "redeemReward";
	}
	@RequestMapping(value = "/reedeemrewardProcess/{rewardId}", method = RequestMethod.POST)
	public String reedeemrewardProcess(@PathVariable  String rewardId,Model model) {
		int rewardid=Integer.parseInt(rewardId);
		RewardsBean rewardsBean=rewardsService.findByRewardid(rewardid);
		if(rewardsBean!=null) {			
			redeemRewardRequest=new RedeemRewardRequest(rewardid,rewardsBean.getRewardName(),rewardsBean.getRewardvalue());
			model.addAttribute("redeemRewardRequest", redeemRewardRequest);
			return "redeemRewardsurity";
		}
		else {
			MessageBean messageBean = new MessageBean("Error", "Some Problem Has Occured");
			model.addAttribute("messageBean", messageBean);
		}
		return "sucesssubmit";
	}

	@RequestMapping(value = "/surityrewardredeem", method = RequestMethod.GET)
	public String reedeemrewardProcess(Model model,Principal principal) {
		Login login=loginService.getLoginByEmailOrUserName(principal.getName());
		int rewardid=redeemRewardRequest.getRewardid();
		RewardsBean rewardsBean=rewardsService.findByRewardid(rewardid);
		if(VerifyObjects.verifyRewardsBean(rewardsBean)) {
			rewardsService.redeemReward(rewardid);
			int currentPoints=pointsService.redeemedPoints(rewardsBean.getRewardvalue(),login);
			MessageBean messageBean = new MessageBean("INFO", "Reward "+rewardsBean.getRewardName()+" reedeemed For " +rewardsBean.getRewardvalue()+"\n current Points :"+currentPoints);
			model.addAttribute("messageBean", messageBean);
		}
		else {
			MessageBean messageBean = new MessageBean("Error", "reward already redeemed or there reward is not there");
			model.addAttribute("messageBean", messageBean);
		}
		return "sucesssubmit";
	}
	@RequestMapping(value = "/deltereward", method = RequestMethod.GET)
	public String deltereward(Model model,Principal principal) {
		Login login=loginService.getLoginByEmailOrUserName(principal.getName());
		List<RewardsBean> redeemeligibleRecords= rewardsService.findeligibleRewards(login);
		model.addAttribute("redeemeligibleRecords", redeemeligibleRecords);
		return "deltereward";
	}
	@RequestMapping(value = "/deltereward/{rewardId}", method = RequestMethod.POST)
	public String delterewardProcess(@PathVariable  String rewardId,Model model) {
		int rewardid=Integer.parseInt(rewardId);
		RewardsBean rewardsBean=rewardsService.findByRewardid(rewardid);
		if(rewardsBean!=null) {			
			redeemRewardRequest=new RedeemRewardRequest(rewardid,rewardsBean.getRewardName(),rewardsBean.getRewardvalue());
			model.addAttribute("redeemRewardRequest", redeemRewardRequest);
			return "deleteRewardsurity";
		}
		else {
			MessageBean messageBean = new MessageBean("Error", "Some Problem Has Occured");
			model.addAttribute("messageBean", messageBean);
		}
		return "sucesssubmit";
	}

	@RequestMapping(value = "/delterewardsurity", method = RequestMethod.GET)
	public String delterewardsure(Model model) {
		int rewardid=redeemRewardRequest.getRewardid();
		RewardsBean rewardsBean=rewardsService.findByRewardid(rewardid);
		if(VerifyObjects.verifyRewardsBeanForDelete(rewardsBean)) {
			rewardsService.deletereward(rewardid);
			MessageBean messageBean = new MessageBean("INFO", "Reward "+rewardsBean.getRewardName()+"got deleted");
			model.addAttribute("messageBean", messageBean);
		}
		else {
			MessageBean messageBean = new MessageBean("Error", "reward"+rewardsBean.getRewardName()+" is not there");
			model.addAttribute("messageBean", messageBean);
		}
		return "sucesssubmit";
	}
	@RequestMapping(value = "/redeemMovies", method = RequestMethod.GET)
	public String redeemMovies(Model model,Principal principal) {
		Login login=loginService.getLoginByEmailOrUserName(principal.getName());
		model.addAttribute("messageBean",  new MessageBean("INFO", ""));
		RedeemMovieRequest redeemMovieRequest= new RedeemMovieRequest();
		List<Movies> netfixMovies=moviesService.findByOtt("Netflix",login);
		logger.info("RewardSystemController.redeemMovies(Model) Netflix :"+gson.toJson(netfixMovies));
		List<Movies> amazonMovies=moviesService.findByOtt("amazon",login);
		logger.info("RewardSystemController.redeemMovies(Model) Netflix :"+gson.toJson(amazonMovies));
		List<Movies> hotstarMovies=moviesService.findByOtt("hotstar",login);
		logger.info("RewardSystemController.redeemMovies(Model) Netflix :"+gson.toJson(hotstarMovies));
		List<Movies> otherMovies=moviesService.findByOtt("other",login);
		logger.info("RewardSystemController.redeemMovies(Model) Netflix :"+gson.toJson(otherMovies));
		model.addAttribute("redeemMovieRequest", redeemMovieRequest);
		model.addAttribute("netfixMovies", netfixMovies);
		model.addAttribute("amazonMovies", amazonMovies);
		model.addAttribute("hotstarMovies", hotstarMovies);
		model.addAttribute("otherMovies", otherMovies);
		return "redeemMovies";
	}
	
	@RequestMapping(value = "/redeemMovies/add", method = RequestMethod.POST)
	public String redeemMoviesAdd(Model model,@ModelAttribute RedeemMovieRequest redeemMovieRequest,Principal principal) {
		model.addAttribute("messageBean",  new MessageBean("INFO", ""));
		Movies movies=new Movies();
		movies.setMovieName(redeemMovieRequest.getMovieName());
		movies.setOtt(redeemMovieRequest.getMovieName());
		logger.info("movieName "+redeemMovieRequest.getMovieName());
		logger.info("ottOption "+redeemMovieRequest.getOttOption());
		if(redeemMovieRequest.getOttOption().equalsIgnoreCase("content_1")) {
			movies.setOtt("hotstar");
		}
		if(redeemMovieRequest.getOttOption().equalsIgnoreCase("content_2")) {
			movies.setOtt("amazon");	
		}
		if(redeemMovieRequest.getOttOption().equalsIgnoreCase("content_3")) {
			movies.setOtt("netflix");
		}
		if(redeemMovieRequest.getOttOption().equalsIgnoreCase("content_4")) {
			movies.setOtt("other");
		}
		Login login=loginService.getLoginByEmailOrUserName(principal.getName());
		moviesService.insertMovie(movies,login);
		List<Movies> netfixMovies=moviesService.findByOtt("Netflix",login);
		logger.info("RewardSystemController.redeemMovies(Model) Netflix :"+gson.toJson(netfixMovies));
		List<Movies> amazonMovies=moviesService.findByOtt("amazon",login);
		logger.info("RewardSystemController.redeemMovies(Model) Netflix :"+gson.toJson(amazonMovies));
		List<Movies> hotstarMovies=moviesService.findByOtt("hotstar",login);
		logger.info("RewardSystemController.redeemMovies(Model) Netflix :"+gson.toJson(hotstarMovies));
		List<Movies> otherMovies=moviesService.findByOtt("other",login);
		logger.info("RewardSystemController.redeemMovies(Model) Netflix :"+gson.toJson(otherMovies));
		model.addAttribute("redeemMovieRequest", redeemMovieRequest);
		model.addAttribute("netfixMovies", netfixMovies);
		model.addAttribute("amazonMovies", amazonMovies);
		model.addAttribute("hotstarMovies", hotstarMovies);
		model.addAttribute("otherMovies", otherMovies);
		
		return "redeemMovies";
	}
	@RequestMapping(value = "/redeemMovies/redeem/{movieId}", method = RequestMethod.GET)
	public String redeemMovie(@PathVariable String movieId,Model model,Principal principal) {
		Login login=loginService.getLoginByEmailOrUserName(principal.getName());
		Movies movie=moviesService.findByMovieId(Integer.parseInt(movieId),login);
		if(VerifyObjects.verifyMovies(movie)) {
			redeemMovieId= new RedeemMovieId(movieId,movie.getOtt(),movie.getMovieName()) ;
			model.addAttribute("redeemMovieId", redeemMovieId);
			return "redeemMoviessurity";
		}
		else {
			MessageBean messageBean = new MessageBean("Error", "Some Problem Has Occured");
			model.addAttribute("messageBean", messageBean);
		}
		return "sucesssubmit";
	}
	@RequestMapping(value = "/redeemMovies/redeemMovie", method = RequestMethod.GET)
	public String redeemMoviesurity(Model model,Principal principal) {
		if(VerifyObjects.verifyRedeemMovieId(redeemMovieId)) {
			Login login=loginService.getLoginByEmailOrUserName(principal.getName());
			String movieId=redeemMovieId.getMovieId();
			pointsService.redeemedPoints(20,login);
			moviesService.setWatchedTrueByMovieId(Integer.parseInt(movieId));
			MessageBean messageBean = new MessageBean("INFO", "Watch the movie "+
					moviesService.getMovieNameByid(Integer.parseInt(movieId),login));
			model.addAttribute("messageBean", messageBean);
		}
		else {
			MessageBean messageBean = new MessageBean("Error", "Some Problem Has Occured");
			model.addAttribute("messageBean", messageBean);
		}
		return "sucesssubmit";
	}
	@RequestMapping(value = "/redeemMovies/remove/{movieId}", method = RequestMethod.GET)
	public String removeMovie(@PathVariable String movieId,Model model,Principal principal) {
		Login login=loginService.getLoginByEmailOrUserName(principal.getName());
		MessageBean messageBean = new MessageBean("INFO", "delete the movie "+
				moviesService.getMovieNameByid(Integer.parseInt(movieId),login));
		model.addAttribute("messageBean", messageBean);
		moviesService.deleteByMovieId(Integer.parseInt(movieId));
		List<Movies> netfixMovies=moviesService.findByOtt("Netflix",login);
		logger.info("RewardSystemController.redeemMovies(Model) Netflix :"+gson.toJson(netfixMovies));
		List<Movies> amazonMovies=moviesService.findByOtt("amazon",login);
		logger.info("RewardSystemController.redeemMovies(Model) Netflix :"+gson.toJson(amazonMovies));
		List<Movies> hotstarMovies=moviesService.findByOtt("hotstar",login);
		logger.info("RewardSystemController.redeemMovies(Model) Netflix :"+gson.toJson(hotstarMovies));
		List<Movies> otherMovies=moviesService.findByOtt("other",login);
		logger.info("RewardSystemController.redeemMovies(Model) Netflix :"+gson.toJson(otherMovies));
		model.addAttribute("netfixMovies", netfixMovies);
		model.addAttribute("amazonMovies", amazonMovies);
		model.addAttribute("hotstarMovies", hotstarMovies);
		model.addAttribute("otherMovies", otherMovies);
		model.addAttribute("redeemMovieRequest", new RedeemMovieRequest());
		return "redeemMovies";
	}
	@RequestMapping(value = "/pointsHistory", method = RequestMethod.GET)
	public String pointsHistory(Model model,Principal principal) {
		Login login=loginService.getLoginByEmailOrUserName(principal.getName());
		List<PointsBean> pointBeans=pointsService.getLast10PointsBean(login);
		model.addAttribute("pointBeans", pointBeans);
		
		return "pointsHistory";
	}
	@RequestMapping(value = "/redeemWebseries", method = RequestMethod.GET)
	public String redeemWebseries(Model model,Principal principal) {
		Login login=loginService.getLoginByEmailOrUserName(principal.getName());
		model.addAttribute("messageBean",  new MessageBean("INFO", ""));
		RedeemWebseriesRequest redeemWebseriesRequest= new RedeemWebseriesRequest();
		List<Webseries> netfixWebseries=WebseriesService.findByOtt("Netflix",login);
		logger.info("RewardSystemController.redeemWebseries(Model) Netflix :"+gson.toJson(netfixWebseries));
		List<Webseries> amazonWebseries=WebseriesService.findByOtt("amazon",login);
		logger.info("RewardSystemController.redeemWebseries(Model) Amazon :"+gson.toJson(amazonWebseries));
		List<Webseries> hotstarWebseries=WebseriesService.findByOtt("hotstar",login);
		logger.info("RewardSystemController.redeemWebseries(Model) hotstar :"+gson.toJson(hotstarWebseries));
		List<Webseries> otherWebseries=WebseriesService.findByOtt("other",login);
		logger.info("RewardSystemController.redeemWebseries(Model) other :"+gson.toJson(otherWebseries));
		model.addAttribute("redeemWebseriesRequest", redeemWebseriesRequest);
		model.addAttribute("netfixWebseries", netfixWebseries);
		model.addAttribute("amazonWebseries", amazonWebseries);
		model.addAttribute("hotstarWebseries", hotstarWebseries);
		model.addAttribute("otherWebseries", otherWebseries);
		return "redeemWebseries";
	}
	@RequestMapping(value = "/redeemWebseries/redeem/{webseriesId}", method = RequestMethod.GET)
	public String redeemWebseries(@PathVariable String webseriesId,Model model,Principal principal) {
		Login login=loginService.getLoginByEmailOrUserName(principal.getName());
		Webseries webseries=WebseriesService.findByWebseriesid(Integer.parseInt(webseriesId));
		if(VerifyObjects.verifyWebSeries(webseries)) {
			redeemWebseriesId= new RedeemWebseriesId(webseriesId,webseries.getOtt(),webseries.getWebseriesname()) ;
			model.addAttribute("redeemWebseriesId", redeemWebseriesId);
			return "redeemWebseriessurity";
		}
		else {
			MessageBean messageBean = new MessageBean("Error", "Some Problem Has Occured");
			model.addAttribute("messageBean", messageBean);
		}
		return "sucesssubmit";
	}
	@RequestMapping(value = "/redeemWebseries/redeemWebseries", method = RequestMethod.GET)
	public String redeemWebseriesurity(Model model,Principal principal) {
		if(VerifyObjects.verifyRedeemWebseriesId(redeemWebseriesId)) {
			Login login=loginService.getLoginByEmailOrUserName(principal.getName());
			String WebseriesId=redeemWebseriesId.getWebseriesId();
			pointsService.redeemedPoints(40,login);
			WebseriesService.setWatchedTrueByWebseriesid(Integer.parseInt(WebseriesId));
			MessageBean messageBean = new MessageBean("INFO", "Watch the Webseries "+
					WebseriesService.getWebseriesNameByid(Integer.parseInt(WebseriesId)));
			model.addAttribute("messageBean", messageBean);
		}
		else {
			MessageBean messageBean = new MessageBean("Error", "Some Problem Has Occured");
			model.addAttribute("messageBean", messageBean);
		}
		return "sucesssubmit";
	}
	@RequestMapping(value = "/redeemWebseries/add", method = RequestMethod.POST)
	public String redeemWebseriesAdd(Model model,@ModelAttribute RedeemWebseriesRequest redeemWebseriesRequest
			,Principal principal) {
		Login login=loginService.getLoginByEmailOrUserName(principal.getName());
		model.addAttribute("messageBean",  new MessageBean("INFO", ""));
		Webseries Webseries=new Webseries();
		Webseries.setWebseriesname(redeemWebseriesRequest.getWebseriesName());
		Webseries.setOtt(redeemWebseriesRequest.getWebseriesName());
		logger.info("WebseriesName "+redeemWebseriesRequest.getWebseriesName());
		logger.info("ottOption "+redeemWebseriesRequest.getOttOption());
		if(redeemWebseriesRequest.getOttOption().equalsIgnoreCase("content_1")) {
			Webseries.setOtt("hotstar");
		}
		if(redeemWebseriesRequest.getOttOption().equalsIgnoreCase("content_2")) {
			Webseries.setOtt("amazon");	
		}
		if(redeemWebseriesRequest.getOttOption().equalsIgnoreCase("content_3")) {
			Webseries.setOtt("netflix");
		}
		if(redeemWebseriesRequest.getOttOption().equalsIgnoreCase("content_4")) {
			Webseries.setOtt("other");
		}
		WebseriesService.insertWebseries(Webseries,login);
		List<Webseries> netfixWebseries=WebseriesService.findByOtt("Netflix",login);
		logger.info("RewardSystemController.redeemWebseries(Model) Netflix :"+gson.toJson(netfixWebseries));
		List<Webseries> amazonWebseries=WebseriesService.findByOtt("amazon",login);
		logger.info("RewardSystemController.redeemWebseries(Model) amazon :"+gson.toJson(amazonWebseries));
		List<Webseries> hotstarWebseries=WebseriesService.findByOtt("hotstar",login);
		logger.info("RewardSystemController.redeemWebseries(Model) hotstar :"+gson.toJson(hotstarWebseries));
		List<Webseries> otherWebseries=WebseriesService.findByOtt("other",login);
		logger.info("RewardSystemController.redeemWebseries(Model) other :"+gson.toJson(otherWebseries));
		model.addAttribute("redeemWebseriesRequest", redeemWebseriesRequest);
		model.addAttribute("netfixWebseries", netfixWebseries);
		model.addAttribute("amazonWebseries", amazonWebseries);
		model.addAttribute("hotstarWebseries", hotstarWebseries);
		model.addAttribute("otherWebseries", otherWebseries);
		
		return "redeemWebseries";
	}
	@RequestMapping(value = "/redeemWebseries/remove/{webseriesId}", method = RequestMethod.GET)
	public String removeWebSeries(@PathVariable String webseriesId,Model model,Principal principal) {
		Login login=loginService.getLoginByEmailOrUserName(principal.getName());
		MessageBean messageBean = new MessageBean("INFO", "delete the Webseries "+
				WebseriesService.getWebseriesNameByid(Integer.parseInt(webseriesId)));
		model.addAttribute("messageBean", messageBean);
		WebseriesService.deleteByWebseriesid(Integer.parseInt(webseriesId));
		List<Webseries> netfixWebseries=WebseriesService.findByOtt("Netflix",login);
		logger.info("RewardSystemController.redeemWebseries(Model) Netflix :"+gson.toJson(netfixWebseries));
		List<Webseries> amazonWebseries=WebseriesService.findByOtt("amazon",login);
		logger.info("RewardSystemController.redeemWebseries(Model) Netflix :"+gson.toJson(amazonWebseries));
		List<Webseries> hotstarWebseries=WebseriesService.findByOtt("hotstar",login);
		logger.info("RewardSystemController.redeemWebseries(Model) Netflix :"+gson.toJson(hotstarWebseries));
		List<Webseries> otherWebseries=WebseriesService.findByOtt("other",login);
		logger.info("RewardSystemController.redeemWebseries(Model) Netflix :"+gson.toJson(otherWebseries));
		model.addAttribute("redeemWebseriesRequest", new RedeemWebseriesRequest());
		model.addAttribute("netfixWebseries", netfixWebseries);
		model.addAttribute("amazonWebseries", amazonWebseries);
		model.addAttribute("hotstarWebseries", hotstarWebseries);
		model.addAttribute("otherWebseries", otherWebseries);
		return "redeemWebseries";
	}
}

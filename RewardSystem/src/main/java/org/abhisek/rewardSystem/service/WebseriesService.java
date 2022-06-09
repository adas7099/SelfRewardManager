package org.abhisek.rewardSystem.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.abhisek.rewardSystem.dao.Login;
import org.abhisek.rewardSystem.dao.Webseries;
import org.abhisek.rewardSystem.repository.LoginRepository;
import org.abhisek.rewardSystem.repository.WebseriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Transactional
public class WebseriesService {
	
	@Autowired
	WebseriesRepository webseriesRepository;
	@Autowired
	LoginRepository loginRepository;
	
	public List<Webseries> findByOtt(String ott,Login login){
		return webseriesRepository.findByOttAndWatchedAndLogin(ott, false,login);
	}
	public Webseries findByWebseriesid(int webseriesid){
		List<Webseries> webseries=webseriesRepository.findByWebseriesid(webseriesid);
		if(null!=webseries &&!CollectionUtils.isEmpty(webseries)) {
			return webseries.get(0);
		}
		else
			return null;
	}
	public void insertWebseries(Webseries webSeries,Login login) {
		webSeries.setLogin(login);
		login.getWebseries().add(webSeries);
		loginRepository.save(login);
		webseriesRepository.save(webSeries);
	}
	public String getWebseriesNameByid(int webseriesid) {
		
		 List<Webseries> webseries=webseriesRepository.findByWebseriesid(webseriesid);
		 if(null!=webseries && CollectionUtils.isEmpty(webseries))
			 return webseries.get(0).getWebseriesname();
		 else
			 return null;
	}
	public void setWatchedTrueByWebseriesid(int webseriesid) {
		webseriesRepository.setWatchedTrueByWebseriesid(webseriesid);
		webseriesRepository.setWatchedDateByWebseriesid(webseriesid, Date.valueOf(LocalDate.now()));
	}
	public void deleteByWebseriesid(int webseriesid) {
		webseriesRepository.deleteByWebseriesid(webseriesid);
	}
	
}

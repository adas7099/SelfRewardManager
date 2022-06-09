package org.abhisek.rewardSystem.repository;

import java.sql.Date;
import java.util.List;

import org.abhisek.rewardSystem.dao.Login;
import org.abhisek.rewardSystem.dao.Webseries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface WebseriesRepository extends JpaRepository<Webseries, Integer>{
	
	public List<Webseries> findByOttAndLogin(String ott,Login login);
	public List<Webseries> findByOttAndWatchedAndLogin(String ott,boolean watched,Login login);
	public List<Webseries> findByWebseriesid(int webseriesid);
	public long deleteByWebseriesid(int webseriesid);
	@Modifying
	@Query(value="update Webseries w set w.watched=true where w.webseriesid=:webseriesid",nativeQuery = true)
	public void setWatchedTrueByWebseriesid(@Param("webseriesid") int webseriesid);
	
	@Modifying
	@Query(value="update Webseries w set w.watchedDate=:watchedDate where w.webseriesid=:webseriesid",nativeQuery = true)
	public void setWatchedDateByWebseriesid(@Param("webseriesid") int webseriesid,@Param("watchedDate") Date watchedDate);
	
}

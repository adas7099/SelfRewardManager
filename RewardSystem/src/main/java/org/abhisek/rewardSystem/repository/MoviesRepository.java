package org.abhisek.rewardSystem.repository;

import java.sql.Date;
import java.util.List;

import org.abhisek.rewardSystem.dao.Login;
import org.abhisek.rewardSystem.dao.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface MoviesRepository extends JpaRepository<Movies, Integer>{
	
	public List<Movies> findByOttAndLogin(String ott,Login login);
	public List<Movies> findByOttAndWatchedAndLogin(String ott,boolean watched,Login login);
	public List<Movies> findByMovieid(int movieid);
	public long deleteByMovieid(int movieid);
	@Modifying
	@Query(value="update Movies m set m.watched=true where m.movieid=:movieid ",nativeQuery = true)
	public void setWatchedTrueByMovieid(@Param("movieid") int movieid);
	
	@Modifying
	@Query(value="update Movies m set m.watchedDate=:watchedDate where m.movieid=:movieid",nativeQuery = true)
	public void setWatchedDateByMovieid(@Param("movieid") int movieid,@Param("watchedDate") Date watchedDate);
	
}

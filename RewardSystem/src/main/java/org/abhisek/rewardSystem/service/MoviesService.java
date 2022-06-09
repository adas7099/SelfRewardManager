package org.abhisek.rewardSystem.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.abhisek.rewardSystem.dao.Login;
import org.abhisek.rewardSystem.dao.Movies;
import org.abhisek.rewardSystem.repository.LoginRepository;
import org.abhisek.rewardSystem.repository.MoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Transactional
public class MoviesService {
	
	@Autowired
	MoviesRepository moviesRepository;
	@Autowired
	LoginRepository loginRepository;
	
	public List<Movies> findByOtt(String ott,Login login){
		return moviesRepository.findByOttAndWatchedAndLogin(ott,false,login);
	}
	public Movies findByMovieId(int movieid,Login login){
		List<Movies> movies=moviesRepository.findByMovieid(movieid);
		if(!CollectionUtils.isEmpty(movies))
			return movies.get(0);
		else
			return null;
	}
	public void insertMovie(Movies movie,Login login) {
		movie.setLogin(login);
		login.getMovies().add(movie);
		loginRepository.save(login);
		moviesRepository.save(movie);	
	}
	public String getMovieNameByid(int movieid,Login login) {
		return moviesRepository.findByMovieid(movieid).get(0).getMovieName();
	}
	public void setWatchedTrueByMovieId(int movieId) {
		moviesRepository.setWatchedTrueByMovieid(movieId);
		moviesRepository.setWatchedDateByMovieid(movieId, Date.valueOf(LocalDate.now()));
	}
	public void deleteByMovieId(int movieId) {
		moviesRepository.deleteByMovieid(movieId);
	}
	
}

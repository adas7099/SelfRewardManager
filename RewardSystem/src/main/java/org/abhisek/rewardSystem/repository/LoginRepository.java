package org.abhisek.rewardSystem.repository;

import java.util.List;

import org.abhisek.rewardSystem.dao.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login, Integer>{
	
	public List<Login> findByUserid(int userid);
	@Query(value="select * from Login l where l.username=:userName or l.email=:userName",nativeQuery = true) 
	public Login getLoginByEmailOrUserName(@Param("userName") String userName);
	
	public long countByUsername(String username);
	
	public long  countByEmail(String email);
}

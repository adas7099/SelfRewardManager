package org.abhisek.rewardSystem.service;

import javax.transaction.Transactional;

import org.abhisek.rewardSystem.dao.Login;
import org.abhisek.rewardSystem.dao.Login;
import org.abhisek.rewardSystem.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LoginService {
	@Autowired
	LoginRepository loginRepository;
	
	public String AlreadyExists(String email,String username) {
		if(loginRepository.countByUsername(username)!=0)
			return "UserName "+username+" alredy Exists";
		if(loginRepository.countByEmail(email)!=0)
			return "Email "+email+" alredy Exists";
		return null;
	}

	public void insert(Login login) {
		// TODO Auto-generated method stub
		loginRepository.save(login);
	}
	

	public Login getLoginByEmailOrUserName(String name) {
		return loginRepository.getLoginByEmailOrUserName(name);
	}
}

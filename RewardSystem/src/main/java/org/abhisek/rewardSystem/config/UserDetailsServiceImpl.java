package org.abhisek.rewardSystem.config;

import org.abhisek.rewardSystem.dao.Login;
import org.abhisek.rewardSystem.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private LoginRepository loginRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Login login=loginRepository.getLoginByEmailOrUserName(username);
		if(null==login) {
			throw new UsernameNotFoundException("User is not found");
		}
		CustomUserDetails customUserDetails=new CustomUserDetails(login);
		return customUserDetails;
	}

}

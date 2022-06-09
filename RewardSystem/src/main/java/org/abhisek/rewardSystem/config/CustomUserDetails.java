package org.abhisek.rewardSystem.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.abhisek.rewardSystem.dao.Login;
import org.abhisek.rewardSystem.service.DailyTimesheetService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.gson.Gson;

import org.slf4j.Logger ;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data 
public class CustomUserDetails implements UserDetails {
	
	public CustomUserDetails(Login user) {
		super();
		this.user = user;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger logger=LoggerFactory.getLogger(CustomUserDetails.class);
	@Autowired
	Gson gson;
	private Login user;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		
		logger.info("Role:"+user.getRole());
		logger.info("user:"+new Gson().toJson(user));
		List<SimpleGrantedAuthority> SimpleGrantedAuthorities=new ArrayList<SimpleGrantedAuthority>();
		SimpleGrantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));
		return SimpleGrantedAuthorities;
	}

	@Override
	public String getPassword() {
		
		return user.getUserpassword();
	}

	@Override
	public String getUsername() {
		
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
	
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}

}

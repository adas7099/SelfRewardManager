package org.abhisek.rewardSystem.bean;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotEmpty(message = "Name cannot null or empty")
	@Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long") 
	private String name;
	@Email(message="Please provide a valid email address")
	@Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
	@NotEmpty(message = "Email cannot null or empty")
	@Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long") 
	private String email;
	@NotEmpty(message = "Password cannot null or empty")
	@Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long") 
	private String password;
	private String conpassword;
}

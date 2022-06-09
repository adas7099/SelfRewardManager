package org.abhisek.rewardSystem.controller;

import org.abhisek.rewardSystem.bean.LoginBean;
import org.abhisek.rewardSystem.bean.MessageBean;
import org.abhisek.rewardSystem.dao.Login;
import org.abhisek.rewardSystem.repository.LoginRepository;
import org.abhisek.rewardSystem.service.DailyTimesheetService;
import org.abhisek.rewardSystem.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
@Controller
public class LoginRegistrationController {
	Logger logger = LoggerFactory.getLogger(LoginRegistrationController.class);
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@Autowired
	LoginService loginService;
	
	@Autowired
	Gson gson;
	@RequestMapping(value="/signup", method = RequestMethod.GET)
	public String signUp(Model model) {
		MessageBean messageBean = new MessageBean("INFO", "");
		model.addAttribute("messageBean", messageBean);
		model.addAttribute("loginBean", new LoginBean());
		return "registration";
	}
	@RequestMapping(value="/signup/submit", method = RequestMethod.POST)
	public String signUpSubmit(@ModelAttribute LoginBean loginBean,Model model,BindingResult result) {
		String msg;
		if(result.hasErrors()) {
			MessageBean messageBean = new MessageBean("ERROR", result.toString());
			model.addAttribute("messageBean", messageBean);
			model.addAttribute("loginBean", new LoginBean());
			return "registration";
		}
		else if((msg=loginService.AlreadyExists(loginBean.getEmail(), loginBean.getName()))!=null) {
			MessageBean messageBean = new MessageBean("ERROR", msg);
			model.addAttribute("messageBean", messageBean);
			model.addAttribute("loginBean", new LoginBean());
			return "registration";
		}
		else if(loginBean.getConpassword()==null ||loginBean.getConpassword().length()<=2) {
			MessageBean messageBean = new MessageBean("ERROR","Confirm Password Cannot be empty");
			model.addAttribute("messageBean", messageBean);
			model.addAttribute("loginBean", new LoginBean());
			return "registration";
		}
		else if(!loginBean.getConpassword().equals(loginBean.getPassword())) {
			MessageBean messageBean = new MessageBean("ERROR","Password doesnot match with Confirm Password");
			model.addAttribute("messageBean", messageBean);
			model.addAttribute("loginBean", new LoginBean());
			return "registration";
		}
		
		Login login=new Login(loginBean);
		login.setUserpassword(passwordEncoder.encode(loginBean.getPassword()));
		login.setRole("USER");
		loginService.insert(login);
		MessageBean messageBean = new MessageBean("INFO", "registration is done");
		model.addAttribute("messageBean", messageBean);
		return "sucesssubmit";
	}
}

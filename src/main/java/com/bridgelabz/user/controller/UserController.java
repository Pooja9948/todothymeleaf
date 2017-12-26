package com.bridgelabz.user.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bridgelabz.user.model.UserDetails;
import com.bridgelabz.user.service.UserService;

/**
 * @author Pooja todoApp
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

	//public static Logger logger = Logger.getLogger(UserController.class);
	@Autowired
	UserService userservice;

	@RequestMapping(value = "/registerpage", method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("user", new UserDetails());
		modelAndView.setViewName("registerpage");
		return modelAndView;
	}

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public ModelAndView save(@Valid UserDetails user) {
		ModelAndView modelAndView = new ModelAndView();

		userservice.createUser(user);
		
		modelAndView.addObject("successMessage", "User is registered");
		modelAndView.setViewName("loginpage");
		return modelAndView;
	}
}

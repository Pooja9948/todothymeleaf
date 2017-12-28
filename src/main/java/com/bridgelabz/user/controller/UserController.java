package com.bridgelabz.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bridgelabz.note.model.NoteDetails;
import com.bridgelabz.note.service.NoteService;
import com.bridgelabz.user.model.UserDetails;
import com.bridgelabz.user.service.UserService;
import com.bridgelabz.util.PasswordEncryption;
import com.bridgelabz.util.token.GenerateToken;

/**
 * @author Pooja todoApp
 *
 */
@Controller
public class UserController {

	// public static Logger logger = Logger.getLogger(UserController.class);
	@Autowired
	UserService userservice;

	/*
	 * @Autowired SendMail sendmail;
	 */

	@Autowired
	PasswordEncryption encryption;
	
	@Autowired
	NoteService noteService;
	
	/**
	 * @return registerpage.html
	 */
	@RequestMapping(value = "/registerpage", method = RequestMethod.GET)
	public ModelAndView registration() {
		System.out.println("inside register url");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("user", new UserDetails());
		modelAndView.setViewName("registerpage");
		return modelAndView;
	}

	/**
	 * @param user
	 * @return save the user and go to the loginpage
	 */
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public ModelAndView save(@Valid UserDetails user, HttpServletRequest request) {
		System.out.println("inside register url save");
		ModelAndView modelAndView = new ModelAndView();
		user.setPassword(PasswordEncryption.encryptedPassword(user.getPassword()));
		int id=userservice.createUser(user);
		if(id != 0) {
			String activeToken = GenerateToken.generateToken(id);
			System.out.println("token "+activeToken);
			String url = request.getRequestURL().toString();
			url = url.substring(0, url.lastIndexOf("/")) + "/" + "verifyMail/" + activeToken;
			modelAndView.addObject("user", new UserDetails());
			modelAndView.addObject("successMessage", "User is registered");
			modelAndView.setViewName("loginpage");
			return modelAndView;
		}
		modelAndView.addObject("errorMessage", "User is registered but not send the mail;");
		modelAndView.setViewName("loginpage");
		return modelAndView;
	}

	/**
	 * @return loginpage.html
	 */
	@RequestMapping(value = "/loginpage", method = RequestMethod.GET)
	public ModelAndView loginUser() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("user", new UserDetails());
		modelAndView.setViewName("loginpage");
		return modelAndView;
	}

	/**
	 * @param user
	 * @return check the user is exist or not. if exist , go to home page and if not, got to login page
	 */
	@RequestMapping(value = "/loginUser", method = RequestMethod.POST)
	public ModelAndView checkLoginUser(@Valid UserDetails user,HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		String activeToken = GenerateToken.generateToken(user.getId());
		System.out.println("inside login user token "+activeToken);
		user.setPassword(PasswordEncryption.encryptedPassword(user.getPassword()));
		session.setAttribute("user", user);
		session.setAttribute("token", activeToken);
		user = userservice.loginUser(user);
		List<NoteDetails> notes=noteService.getAllNotes(user);
		if(user!=null) {
			modelAndView.addObject("note", new NoteDetails());
			modelAndView.addObject("notes", notes);
			modelAndView.addObject("successMessage", "User is logged in");
			modelAndView.addObject("userName", "Welcome " + user.getFirstname() + " " + user.getLastname() + " (" + user.getEmail() + ")");
			modelAndView.setViewName("homepage");
			return modelAndView;
		}else {
			modelAndView.addObject("user", new UserDetails());
			modelAndView.addObject("successMessage", "User is  not logged in");
			modelAndView.setViewName("loginpage");
			return modelAndView;
		}
	}
}

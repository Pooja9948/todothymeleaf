package com.bridgelabz.note.controller;

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

/**
 * @author Pooja todoApp
 *
 */
@Controller
@RequestMapping(value = "/note")
public class NoteController {

	@Autowired
	NoteService noteService;

	/*@Autowired
	UserService userService;*/

	@RequestMapping(value = "/createNote", method = RequestMethod.POST)
	public ModelAndView createNote(@Valid NoteDetails noteDetails,HttpSession session
			) throws Exception {
		System.out.println("inside create note");
		UserDetails user=(UserDetails) session.getAttribute("user");
		noteDetails.setUser(user);
		NoteDetails note = noteService.createNote(noteDetails);
		ModelAndView modelAndView = new ModelAndView();
		return modelAndView;
	}

	
}

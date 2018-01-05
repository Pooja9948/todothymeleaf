package com.bridgelabz.note.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bridgelabz.note.model.NoteDetails;
import com.bridgelabz.note.service.NoteService;
import com.bridgelabz.user.model.UserDetails;
import com.bridgelabz.user.service.UserService;

/**
 * @author Pooja todoApp
 *
 */
@Controller
@RequestMapping(value = "/user")
public class NoteController {

	@Autowired
	NoteService noteService;

	@Autowired
	UserService userservice;
	
	@RequestMapping(value="/home")
	public ModelAndView home(HttpServletRequest request,HttpSession session)
	{
		ModelAndView modelAndView = new ModelAndView();
		UserDetails user=(UserDetails) session.getAttribute("user");
	
		modelAndView.addObject("userName", "Welcome " + user.getFirstname() + " " + user.getLastname() + " (" + user.getEmail() + ")");
		
		modelAndView.setViewName("homepage");
		modelAndView.addObject("user", user);
		
		NoteDetails note = new NoteDetails();
		modelAndView.addObject("note", note);
		
		List<NoteDetails> notes = noteService.getAllNotes(user);
		modelAndView.addObject("notes", notes);
		return modelAndView;
	}
	
	@RequestMapping(value = "/createNote", method = RequestMethod.POST)
	public ModelAndView createNote(@Valid NoteDetails noteDetails,HttpSession session
			) throws Exception {
		System.out.println("inside create note");
		UserDetails user=(UserDetails) session.getAttribute("user");
		UserDetails user1=userservice.getUserByEmail(user.getEmail());
		System.out.println("users details "+user1.getFirstname()+" "+user1.getEmail()+" "+user1.getLastname()+" "+user1.getMobileno());
		Date date = new Date();
		noteDetails.setCreateddate(date);
		noteDetails.setModifiedDate(date);
		noteDetails.setUser(user1);
		noteService.createNote(noteDetails);
		return new ModelAndView("redirect:/user/home");
		/*modelAndView.addObject("note", new NoteDetails());
		modelAndView.addObject("notes", notes);
		modelAndView.addObject("userName", "Welcome " + user1.getFirstname() + " " + user1.getLastname() + " (" + user1.getEmail() + ")");
		modelAndView.setViewName("homepage");
		return modelAndView;*/
	}
	
	@RequestMapping(value = "/deleteNote/{id}", method = RequestMethod.DELETE)
	public ModelAndView deleteNote(@PathVariable("id") int id, HttpServletRequest request,HttpSession session) {
		
	
		int user_id = (int) request.getAttribute("userId");
		
		System.out.println("user id is => "+user_id+" note id is "+id);
		
		
		//int user_id=noteService.getUserByNoteId(id);
		
		NoteDetails note = new NoteDetails();
		note.setId(id);
		System.out.println("id : " + id);
		
		
		noteService.deleteNote(id);
		System.out.println("note is deleted");
		return new ModelAndView("redirect:/user/home");
		/*modelAndView.addObject("note", new NoteDetails());
		modelAndView.setViewName("homepage");
		return modelAndView;*/
	}
	
	@GetMapping(value="/editNote/{id}")
	public ModelAndView editNote(@PathVariable int id,HttpSession session) {
		UserDetails noteUser=(UserDetails) session.getAttribute("user");
		NoteDetails note = noteService.getNoteById(id);
		session.setAttribute("createTime", note.getCreateddate());
		ModelAndView modelAndView=new ModelAndView();
		modelAndView.setViewName("noteEdit");
		modelAndView.addObject("note",note);
		return modelAndView;
	}
	
	@PutMapping(value="/updateNote/{id}")
	public ModelAndView update(@PathVariable int id,HttpSession session,HttpServletRequest request)
	{
		System.out.println("inside update note");
		NoteDetails noteDetails=new NoteDetails();
		int user_id = (int) request.getAttribute("userId");
		System.out.println("inside update note");
		UserDetails user=(UserDetails) session.getAttribute("user");
		System.out.println("inside update note user emailid is --->"+user.getEmail());
		UserDetails user1=userservice.getUserByEmail(user.getEmail());
		System.out.println("users details "+user1.getId()+" "+user1.getFirstname()+" "+user1.getEmail()+" "+user1.getLastname()+" "+user1.getMobileno()+"noteid----> "+noteDetails.getId());
		noteDetails.setUser(user1);
		noteService.updateNote(noteDetails);
		List<NoteDetails> notes=noteService.getAllNotes(user1);
		ModelAndView modelAndView = new ModelAndView("redirect:/user/home");
		modelAndView.addObject("note", new NoteDetails());
		modelAndView.addObject("notes", notes);
		modelAndView.addObject("userName", "Welcome " + user1.getFirstname() + " " + user1.getLastname() + " (" + user1.getEmail() + ")");
		modelAndView.setViewName("homepage");
		return modelAndView;
		
	}
	
	@RequestMapping(value = "/archiveN", method = RequestMethod.GET)
	public ModelAndView archive(HttpServletRequest request) {
		System.out.println("inside archive url");
		int user_id = (int) request.getAttribute("userId");
		UserDetails user1=userservice.getUserById(user_id);
		NoteDetails noteDetails=noteService.getNoteById(user_id);
		List<NoteDetails> notes=noteService.getArchiveNotes(user1);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("note", new NoteDetails());
		modelAndView.addObject("notes", notes);
		modelAndView.addObject("userName", "Welcome " + user1.getFirstname() + " " + user1.getLastname() + " (" + user1.getEmail() + ")");
		modelAndView.setViewName("archive");
		return modelAndView;
	}
	
	@RequestMapping(value = "/trashN", method = RequestMethod.GET)
	public ModelAndView trash(HttpServletRequest request) {
		System.out.println("inside trash url");
		int user_id = (int) request.getAttribute("userId");
		UserDetails user1=userservice.getUserById(user_id);
		NoteDetails noteDetails=noteService.getNoteById(user_id);
		List<NoteDetails> notes=noteService.getTrashNotes(user1);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("note", new NoteDetails());
		modelAndView.addObject("notes", notes);
		modelAndView.addObject("userName", "Welcome " + user1.getFirstname() + " " + user1.getLastname() + " (" + user1.getEmail() + ")");
		modelAndView.setViewName("trash");
		return modelAndView;
	}
	
	@RequestMapping(value = "/archiveNote/{id}", method = RequestMethod.POST)
	public ModelAndView archiveNote(@PathVariable("id") int id, HttpServletRequest request,HttpSession session) {
	
		System.out.println("inside archive note");
		int user_id = (int) request.getAttribute("userId");
		System.out.println("user id under archive note "+user_id+" note id "+id);
		//UserDetails user1=userservice.getUserById(user_id);
		NoteDetails noteDetails=noteService.getNoteById(id);
		if(noteDetails.getArchived()==true)
			noteDetails.setArchived(false);
		else
			noteDetails.setArchived(true);
		noteService.updateNote(noteDetails);
		//List<NoteDetails> notes=noteService.getAllNotes(user1);
		return new ModelAndView("redirect:/user/home");
		/*modelAndView.addObject("note", new NoteDetails());
		modelAndView.addObject("notes", notes);
		modelAndView.addObject("userName", "Welcome " + user1.getFirstname() + " " + user1.getLastname() + " (" + user1.getEmail() + ")");
		modelAndView.setViewName("homepage");
		return modelAndView;*/
	}
	
	@RequestMapping(value="/trashNote/{id}", method = RequestMethod.PUT)
	public ModelAndView trash(@PathVariable int id,HttpSession session,HttpServletRequest request)
	{
		System.out.println("inside trash note");
		int user_id = (int) request.getAttribute("userId");
		System.out.println("user id under trash note "+user_id+" note id "+id);
		UserDetails user1=userservice.getUserById(user_id);
		NoteDetails noteDetails=noteService.getNoteById(id);
		if(noteDetails.isTrash()==true)
			noteDetails.setTrash(false);
		else
			noteDetails.setTrash(true);
		noteService.updateNote(noteDetails);
		List<NoteDetails> notes=noteService.getAllNotes(user1);
		return new ModelAndView("redirect:/user/home");
		/*modelAndView.addObject("note", new NoteDetails());
		modelAndView.addObject("notes", notes);
		modelAndView.addObject("userName", "Welcome " + user1.getFirstname() + " " + user1.getLastname() + " (" + user1.getEmail() + ")");
		modelAndView.setViewName("homepage");
		return modelAndView;*/
	}
	
	@PutMapping(value="/makeCopyNote/{id}")
	public ModelAndView makeCopy(@PathVariable int id,HttpSession session,HttpServletRequest request)
	{
		System.out.println("inside makeCopy note");
		int user_id = (int) request.getAttribute("userId");
		System.out.println("user id under makeCopy note "+user_id+" note id "+id);
		UserDetails user1=userservice.getUserById(user_id);
		NoteDetails noteDetails=noteService.getNoteById(id);
		Date date = new Date();
		noteDetails.setCreateddate(date);
		noteDetails.setModifiedDate(date);
		noteDetails.setUser(user1);
		noteService.createNote(noteDetails);
		List<NoteDetails> notes=noteService.getAllNotes(user1);
		return new ModelAndView("redirect:/user/home");
		/*modelAndView.addObject("note", new NoteDetails());
		modelAndView.addObject("notes", notes);
		modelAndView.addObject("userName", "Welcome " + user1.getFirstname() + " " + user1.getLastname() + " (" + user1.getEmail() + ")");
		modelAndView.setViewName("homepage");
		return modelAndView;*/
	}
}

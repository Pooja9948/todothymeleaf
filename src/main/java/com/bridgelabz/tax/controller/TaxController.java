package com.bridgelabz.tax.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bridgelabz.tax.model.TaxDetails;
import com.bridgelabz.tax.service.TaxService;
import com.bridgelabz.user.model.UserDetails;

@Controller
public class TaxController {
	
	@Autowired
	TaxService taxService;
	
	@RequestMapping(value="/taxlist")
	public ModelAndView home(HttpServletRequest request,HttpSession session)
	{
		ModelAndView modelAndView = new ModelAndView();
		UserDetails user=(UserDetails) session.getAttribute("user");
	
		modelAndView.setViewName("taxlist");
		//modelAndView.addObject("user", user);
		
		TaxDetails tax = new TaxDetails();
		modelAndView.addObject("tax", tax);
		
		List<TaxDetails> taxes = taxService.getAllTaxes();
		modelAndView.addObject("taxes", taxes);
		return modelAndView;
	}
}

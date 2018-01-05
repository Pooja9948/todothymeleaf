package com.bridgelabz.tax.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.note.model.NoteDetails;
import com.bridgelabz.tax.dao.TaxDao;
import com.bridgelabz.tax.model.TaxDetails;

public class TaxServiceImpl {
	
	@Autowired
	TaxDao taxDao;
	
	public List<TaxDetails> getAllTaxes(){
		return taxDao.getAllTaxes();
	}
}

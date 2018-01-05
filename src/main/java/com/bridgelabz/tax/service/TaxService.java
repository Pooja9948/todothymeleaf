package com.bridgelabz.tax.service;

import java.util.List;

import com.bridgelabz.note.model.NoteDetails;
import com.bridgelabz.tax.model.TaxDetails;

public interface TaxService {
	
	public List<TaxDetails> getAllTaxes();
}

package com.bridgelabz.tax.dao;

import java.util.List;

import com.bridgelabz.note.model.NoteDetails;
import com.bridgelabz.tax.model.TaxDetails;

public interface TaxDao {
	public List<TaxDetails> getAllTaxes();
}

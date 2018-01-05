package com.bridgelabz.tax.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import com.bridgelabz.tax.model.TaxDetails;
import com.bridgelabz.user.model.UserDetails;

public class TaxDaoImpl {
	
	@Autowired
	SessionFactory sessionfactory;
	
	Transaction transaction = null;
	
	public List<TaxDetails> getAllTaxes() {
		Session session=sessionfactory.openSession();
		try
		{
			Criteria criteria = session.createCriteria(TaxDetails.class);
			List list = criteria.list();
			return list;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return null;
	}
}

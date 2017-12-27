package com.bridgelabz.note.dao;

import java.util.List;

//import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.criterion.Order;
import com.bridgelabz.note.model.NoteDetails;
import com.bridgelabz.user.model.UserDetails;

/**
 * @author Pooja todoApp
 *
 */
public class NoteDaoImpl implements NoteDao {

	//public static Logger logger = Logger.getLogger(NoteDAOImpl.class);
	@Autowired
	SessionFactory sessionfactory;
	Transaction transaction = null;

	
	public NoteDetails createNote(NoteDetails note) {
		Session session = sessionfactory.openSession();

		try {
			transaction = session.beginTransaction();
			session.save(note);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return note;
	}

	@Override
	public List<NoteDetails> getAllNotes(UserDetails user) {
		Session session=sessionfactory.openSession();
		try
		{
			Criteria criteria = session.createCriteria(NoteDetails.class);
			criteria.add(Restrictions.eq("userDetails", user));
			criteria.addOrder(Order.desc("modifiedDate"));
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

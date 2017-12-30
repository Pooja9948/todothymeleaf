package com.bridgelabz.note.dao;

import java.util.Date;
import java.util.List;

//import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
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
	@Override
	public int getUserByNoteId(int noteid) {
		Session session = sessionfactory.openSession();
		NoteDetails noteDetails = session.get(NoteDetails.class, noteid);
		System.out.println("User by id is: " + noteDetails);
		session.close();
		return noteDetails.getId();
		
	}
	public void deleteNote(int noteId) {
		Session session = sessionfactory.openSession();

		System.out.println("jhdbvj " + noteId);
		try {
			transaction = session.beginTransaction();
			Query deleteNote = session.createQuery("delete from NoteDetails where id=:noteId");
			deleteNote.setParameter("noteId", noteId);

			deleteNote.executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

	}
	public void updateNote(NoteDetails noteDetails) {
		Session session = sessionfactory.openSession();

		try {
			transaction = session.beginTransaction();
			Date date = new Date();
			noteDetails.setModifiedDate(date);
			session.update(noteDetails);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

	}
}

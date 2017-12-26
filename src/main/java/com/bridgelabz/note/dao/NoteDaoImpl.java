package com.bridgelabz.note.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.query.Query;

//import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.note.model.NoteCollaborate;
import com.bridgelabz.note.model.NoteDetails;
import com.bridgelabz.note.model.NoteLabel;
import com.bridgelabz.note.model.NoteUrl;
import com.bridgelabz.user.controller.UserController;
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

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.DAO.NoteDAO#createNote(com.bridgelabz.Note.model.NoteDetails)
	 */
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

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.DAO.NoteDAO#updateNote(com.bridgelabz.Note.model.NoteDetails)
	 */
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

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.DAO.NoteDAO#deleteNote(int)
	 */
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

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.DAO.NoteDAO#getNoteById(int)
	 */
	public NoteDetails getNoteById(int noteId) {
		Session session = sessionfactory.openSession();
		NoteDetails note = session.get(NoteDetails.class, noteId);
		return note;
	}

	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	@Override
	public List<NoteDetails> getAllNotes(UserDetails userDetails) {
		Session session = sessionfactory.openSession();

		System.out.println("user id ---+++++-----++++ " + userDetails.getId());
		int uid = userDetails.getId();

		/*
		 * Query getNote = session.createQuery(
		 * "from NoteDetails where user_detailid=:uid or user_detailid IN (select ownerId_user_detailid from NoteCollaborate where shareId_user_detailid =:uid ) order by modifiedDate desc"
		 * ); getNote.setParameter("uid", uid);
		 */

		// Query getCollaborateNote = session.createQuery("");
		// getCollaborateNote.setParameter("uid", uid);

		UserDetails user = session.get(UserDetails.class, uid);
		Criteria criteria = session.createCriteria(NoteDetails.class);
		criteria.add(Restrictions.eq("userDetails", user));
		criteria.addOrder(Order.desc("modifiedDate"));
		List notes = criteria.list();

		// List notes = getNote.list();
		// logger.trace(criteria.list());
		// logger.info(getNote.list());
		return notes;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.DAO.NoteDAO#deleteScheduleNote()
	 */
	public void deleteScheduleNote() {
		Session session = sessionfactory.openSession();

		System.out.println("jhdbvj ");
		// Date currentdate=new Date();
		Date deleteTime = new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000);
		boolean trash = true;
		try {
			transaction = session.beginTransaction();
			Query deleteNote = session
					.createQuery("delete from NoteDetails where modifiedDate<:deleteTime and isTrash=:trash");
			deleteNote.setParameter("deleteTime", deleteTime);
			deleteNote.setParameter("trash", trash);

			int count = deleteNote.executeUpdate();
			System.out.println("Number of notes deleted: " + count);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.DAO.NoteDAO#deleteAllNote()
	 */
	public void deleteAllNote() {
		Session session = sessionfactory.openSession();

		boolean trash = true;
		try {
			transaction = session.beginTransaction();
			Query deleteNote = session.createQuery("delete from NoteDetails where  isTrash=:trash");
			deleteNote.setParameter("trash", trash);

			int count = deleteNote.executeUpdate();
			System.out.println("Number of notes deleted: " + count);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.DAO.NoteDAO#saveCollborator(com.bridgelabz.Note.model.NoteCollaborate)
	 */
	public int saveCollborator(NoteCollaborate collborate) {
		int collboratorId = 0;
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			collboratorId = (Integer) session.save(collborate);
			transaction.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			session.close();
		}
		return collboratorId;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.DAO.NoteDAO#getListOfUser(int)
	 */
	public List<UserDetails> getListOfUser(int noteId) {

		Session session = sessionfactory.openSession();
		Query querycollab = session.createQuery("select c.shareId from NoteCollaborate c where c.noteId= " + noteId);
		List<UserDetails> listOfSharedCollaborators = querycollab.list();
		System.out.println("listOfSharedCollaborators " + listOfSharedCollaborators);
		session.close();
		return listOfSharedCollaborators;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.DAO.NoteDAO#getCollboratedNotes(int)
	 */
	public List<NoteDetails> getCollboratedNotes(int userId) {
		Session session = sessionfactory.openSession();
		Query query = session.createQuery("select c.noteId from NoteCollaborate c where c.shareId= " + userId);
		List<NoteDetails> colllboratedNotes = query.list();
		// Set<NoteDetails> notes = new HashSet<NoteDetails>(colllboratedNotes);

		session.close();
		return colllboratedNotes;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.DAO.NoteDAO#removeCollborator(int, int)
	 */
	public int removeCollborator(int shareWith, int noteId) {
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session
				.createQuery("delete  NoteCollaborate c where c.shareId= " + shareWith + " and c.noteId=" + noteId);

		int status = query.executeUpdate();
		session.close();
		return status;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.DAO.NoteDAO#saveLabel(com.bridgelabz.Note.model.NoteLabel)
	 */
	@Override
	public void saveLabel(NoteLabel label) {
		Session session = sessionfactory.openSession();
		transaction = session.beginTransaction();
		try {
			System.out.println("inside save label");
			session.save(label);
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.DAO.NoteDAO#deleteById(int)
	 */
	@Override
	public void deleteById(int id) {
		Session session = sessionfactory.openSession();
		transaction = (Transaction) session.beginTransaction();
		Criteria criteria = session.createCriteria(NoteLabel.class);
		criteria.add(Restrictions.eq("id", id));
		NoteLabel labels = (NoteLabel) criteria.uniqueResult();
		
		try {
			System.out.println("inside delete label dao impl"+labels.getLabelId());
			session.delete(labels);
			transaction.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.DAO.NoteDAO#getLabels(com.bridgelabz.User.model.UserDetails)
	 */
	@Override
	public List<NoteLabel> getLabels(UserDetails user) {
		Session session = sessionfactory.openSession();
		// transaction = (Transaction) session.beginTransaction();
		Criteria criteria = session.createCriteria(NoteLabel.class);
		criteria.add(Restrictions.eqOrIsNull("user", user));
		List<NoteLabel> labels = criteria.list();
		return labels;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.DAO.NoteDAO#getLabelById(int)
	 */
	@Override
	public NoteLabel getLabelById(final int labelId) {
		NoteLabel objLabel = null;
		Session session = sessionfactory.openSession();
		transaction = session.beginTransaction();
		try {
			objLabel = (NoteLabel) session.get(NoteLabel.class, labelId);
			transaction.commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
				session.close();
				return null;
			}
			e.printStackTrace();
		}
		return objLabel;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.DAO.NoteDAO#editLabel(com.bridgelabz.Note.model.NoteLabel)
	 */
	@Override
	public boolean editLabel(NoteLabel label) {
		Session session = sessionfactory.openSession();

		try {
			transaction = session.beginTransaction();
			session.update(label);			
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.DAO.NoteDAO#getLabelByName(java.lang.String)
	 */
	@Override
	public NoteLabel getLabelByName(String labelName) {
		NoteLabel objLabel = null;
		Session session = sessionfactory.openSession();
		Criteria criteria = session.createCriteria(NoteLabel.class);
		criteria.add(Restrictions.eq("labelName", labelName));
		objLabel = (NoteLabel) criteria.uniqueResult();
		session.close();
		return objLabel;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.DAO.NoteDAO#removeNoteId(int)
	 */
	@Override
	public boolean removeNoteId(int id) {
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			
			Query query = session
					.createQuery("delete id NoteLabel where id="+ id);

			int status = query.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/*SAVE URLS*/
	/* (non-Javadoc)
	 * @see com.bridgelabz.Note.DAO.NoteDAO#saveNoteUrls(java.util.Set, com.bridgelabz.Note.model.NoteDetails)
	 */
	@Override
	public void saveNoteUrls(Set<NoteUrl> noteUrls, NoteDetails noteDetails) {
		Session session = sessionfactory.openSession();
		transaction = session.beginTransaction();
		System.out.println("inside save note url dao impl");
		for(NoteUrl urls : noteUrls){
			urls.setNote(noteDetails);
			session.save(urls);
			transaction.commit();
		}
		
	}

	@Override
	public int getUserByNoteId(int noteid) {
		Session session = sessionfactory.openSession();
		NoteDetails noteDetails = session.get(NoteDetails.class, noteid);
		System.out.println("User by id is: " + noteDetails);
		session.close();
		return noteDetails.getId();
		
	}

	
}

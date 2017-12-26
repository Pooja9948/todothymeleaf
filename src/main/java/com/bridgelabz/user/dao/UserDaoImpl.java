package com.bridgelabz.user.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.user.model.UserDetails;


/**
 * @author Pooja todoApp
 *
 */
public class UserDaoImpl implements UserDao {

	/**
	 * @param userDetails
	 * @return inserting all the data of user to the database
	 */

	@Autowired
	SessionFactory sessionFactory;

	private static final String key = "Token";

	//private HashOperations<String, String, Token> hashops;

	
	public int registration(UserDetails userDetails) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			int id = (int) session.save(userDetails);
			transaction.commit();
			return id;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		} finally {
			try {
				if (session != null)
					session.close();
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
	}


	@Override
	public UserDetails loginUser(UserDetails userDetails) {
		System.out.println("inside login user");
		Session session = sessionFactory.openSession();
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(UserDetails.class);
		criteria.add(Restrictions.eq("email", userDetails.getEmail()));
		criteria.add(Restrictions.eq("password", userDetails.getPassword()));
		criteria.add(Restrictions.eq("isActivated", true));
		UserDetails user = (UserDetails) criteria.uniqueResult();

		if (user == null) {
			return null;

		}
		return user;
	}


}

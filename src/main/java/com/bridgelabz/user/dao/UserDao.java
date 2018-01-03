package com.bridgelabz.user.dao;

import com.bridgelabz.user.model.UserDetails;

/**
 * @author Pooja todoApp
 *
 */
public interface UserDao {

	/**
	 * @param userDetails
	 * @return register the user by userDetails object
	 */
	public int registration(UserDetails userDetails);

	/**
	 * @param user
	 * @return checking the user is exist or not
	 */
	public UserDetails loginUser(UserDetails user);

	public UserDetails getUserByEmail(String email);
	
	public UserDetails getUserById(int id);
}

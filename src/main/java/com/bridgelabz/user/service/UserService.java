package com.bridgelabz.user.service;

import com.bridgelabz.user.model.UserDetails;

/**
 * @author Pooja todoApp
 *
 */
public interface UserService {

	/**
	 * @param user
	 * @return create user
	 */
	public int createUser(UserDetails user);

	/**
	 * @param user
	 * @return checking the user is exist or not
	 */
	public UserDetails loginUser(UserDetails user);
}

package com.bridgelabz.user.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.user.dao.UserDao;
import com.bridgelabz.user.model.UserDetails;

/**
 * @author Pooja todoApp
 *
 */
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userdao;

	public int createUser(UserDetails user) {
		return userdao.registration(user);
	}

	public UserDetails loginUser(UserDetails user) {
		return userdao.loginUser(user);
	}

	public UserDetails getUserByEmail(String email) {
		return userdao.getUserByEmail(email);
	}
	public UserDetails getUserById(int id) {
		return userdao.getUserById(id);
	}
}

package com.bridgelabz.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.user.dao.UserDao;
import com.bridgelabz.user.model.UserDetails;


/**
 * @author Pooja todoApp
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userdao;

	public int createUser(UserDetails user) {
		return userdao.registration(user);
	}
}

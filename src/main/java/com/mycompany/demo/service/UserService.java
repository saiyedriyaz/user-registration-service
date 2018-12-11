package com.mycompany.demo.service;

import java.util.List;

import com.mycompany.demo.dto.User;
import com.mycompany.demo.exception.UserAlreadyExistsException;
import com.mycompany.demo.exception.UserBlackListedException;

/**
 * Created by jt on 1/10/17.
 */
public interface UserService {

	/**
	 * This method registers a user if he is not part of a blacklist and not already registered.
	 * 
	 * @param userTO Details of registering user
	 * @return a registered user details
	 * @throws UserBlackListedException if user is blacklisted
	 * @throws UserAlreadyExistsException if user with same user name or/and SSN already exists
	 */
	User registerUser(User userTO) throws UserAlreadyExistsException, UserBlackListedException;

	/**
	 * This method the list of all registered users.
	 * 
	 * @return a list of registered users.
	 */
	List<User> listAllUser();
}

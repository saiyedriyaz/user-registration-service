package com.mycompany.demo.service.impl;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mycompany.demo.dto.User;
import com.mycompany.demo.entity.UserModel;
import com.mycompany.demo.exception.UserAlreadyExistsException;
import com.mycompany.demo.exception.UserBlackListedException;
import com.mycompany.demo.lookup.ExclusionServiceLookup;
import com.mycompany.demo.repository.UserRepository;
import com.mycompany.demo.service.ExclusionService;
import com.mycompany.demo.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Riyaz Saiyed (saiyedriyaz@gmail.com)
 * @version 1.0, 01 Dec 2018
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository<UserModel> userRepository;

	private ExclusionService exclusionService = ExclusionServiceLookup.getExclusionServiceInstance();

	/* (non-Javadoc)
	 * @see com.demo.mycompany.service.UserService#registerUser(com.demo.mycompany.dto.User)
	 */
	@Override
	public User registerUser(User userTO) throws UserBlackListedException, UserAlreadyExistsException {

		if (!exclusionService.validate(userTO.getDateOfBirth(), userTO.getSocialSecurityNumber())) {
			throw new UserBlackListedException("User is black listed." + userTO.getUserName());
		}

		UserModel user = new UserModel(userTO);
		try {
			userRepository.save(user);
			userTO.setId(user.getId());
		} catch (DataAccessException exception) {
			log.info("throwing exception. user already existes-->" + user.getUserName());

			throw new UserAlreadyExistsException("User already exists..!");
		}
		return userTO;
	}

	/* (non-Javadoc)
	 * @see com.demo.mycompany.service.UserService#listAllUser()
	 */
	@Override
	public List<User> listAllUser() {
		List<UserModel> allEntities = (List<UserModel>) userRepository.findAll();

		List<User> allTos = allEntities.parallelStream().map(new Function<UserModel, User>() {
			@Override
			public User apply(UserModel s) {
				return new User(s);
			}
		}).collect(Collectors.toList());

		return allTos;
	}
}

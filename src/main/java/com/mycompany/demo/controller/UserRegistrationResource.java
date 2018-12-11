package com.mycompany.demo.controller;

import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.demo.constant.CommonCodes;
import com.mycompany.demo.dto.BaseResponse;
import com.mycompany.demo.dto.User;
import com.mycompany.demo.exception.UserAlreadyExistsException;
import com.mycompany.demo.exception.UserBlackListedException;
import com.mycompany.demo.service.UserService;
import com.mycompany.demo.validator.UserValidator;

import lombok.extern.slf4j.Slf4j;

/**
 * A spring boot web application that offers a remote service interface (endpoint) to register a user.
 *
 * @author Riyaz Saiyed (saiyedriyaz@gmail.com)
 * @version 1.0, 01 Dec 2018
 */

@RestController
@RequestMapping("/user/service/")
@Slf4j
public class UserRegistrationResource {


	@Autowired
	private UserValidator userValidator;

	@Autowired
	private UserService userService;

	@InitBinder("user")
	public void setupBinder(WebDataBinder binder) {
		binder.addValidators(userValidator);
	}

	/**
	 * This method registers a user if he is not part of a blacklist and not already registered.
	 * 
	 * @param userTO Details of registering user
	 * @return a registered user details
	 * @throws UserBlackListedException if user is blacklisted
	 * @throws UserAlreadyExistsException if user with same user name or/and SSN already exists
	 */
	@PostMapping(value = "register", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveFlightMonitorDetails(@Valid @RequestBody User userTO)
			throws UserBlackListedException, UserAlreadyExistsException {

		User serviceResponse = userService.registerUser(userTO);
		
		BaseResponse baseTo = new BaseResponse();
		baseTo.setTimestamp(Calendar.getInstance().getTime());
		baseTo.setStatus(HttpStatus.OK.value());
		baseTo.setUser(serviceResponse);
		
		return new ResponseEntity<>(baseTo, HttpStatus.OK);
	}
	
	/**
	 * This method the list of all registered users.
	 * 
	 * @return a list of registered users.
	 */
	@GetMapping(value = "listall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> listAllUsers() {
		List<User> serviceResponse = userService.listAllUser();
		
		BaseResponse baseTo = new BaseResponse();
		baseTo.setTimestamp(Calendar.getInstance().getTime());
		baseTo.setStatus(HttpStatus.OK.value());
		baseTo.setAllUsers(serviceResponse);
		
		return new ResponseEntity<>(baseTo, HttpStatus.OK);
	}
	

	@ExceptionHandler(UserAlreadyExistsException.class)
	private ResponseEntity<BaseResponse> handleException(UserAlreadyExistsException e) {
		return createResponse(CommonCodes.ERROR_CODE.USER_EXISTS.getCode(),
				CommonCodes.ERROR_CODE.USER_EXISTS.getMessage(), e);
	}

	@ExceptionHandler(UserBlackListedException.class)
	private ResponseEntity<BaseResponse> handleException(UserBlackListedException e) {
		return createResponse(CommonCodes.ERROR_CODE.USER_BLACKLISTED.getCode(),
				CommonCodes.ERROR_CODE.USER_BLACKLISTED.getMessage(), e);
	}

	private ResponseEntity<BaseResponse> createResponse(String errorCode, String errorMessage, Exception e) {
		BaseResponse baseTo = new BaseResponse();
		
		baseTo.setStatus(HttpStatus.BAD_REQUEST.value());
		
		com.mycompany.demo.dto.Error error = new com.mycompany.demo.dto.Error();

		error.setErrorCode(errorCode);
		error.setErrorDescription(errorMessage);

		baseTo.setError(error);

		log.error("Error occured: Details " + e);

		return new ResponseEntity<>(baseTo, HttpStatus.BAD_REQUEST);
	}
}

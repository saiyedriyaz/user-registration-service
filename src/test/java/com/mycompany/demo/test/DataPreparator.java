package com.mycompany.demo.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.demo.dto.User;
import com.mycompany.demo.entity.UserModel;

public class DataPreparator {

	protected User prepareTO() {
		return prepareTO("saiyedriyaz");
	}

	protected User prepareTO(String userName) {
		User userTO = prepareTO(userName, "Test123", "19986-12-31");
		userTO.setSocialSecurityNumber("social-sec-number");
		return userTO;
	}

	protected User prepareTO(String userName, String paswword, String dateOfBirth) {
		User userTO = new User();
		userTO.setUserName(userName);
		userTO.setPassword(paswword);
		userTO.setDateOfBirth(dateOfBirth);
		return userTO;
	}

	protected UserModel prepareMockEntity(User userTO) {
		UserModel user = new UserModel(userTO);
		user.setId(1l);
		return user;
	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}
}

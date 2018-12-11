package com.mycompany.demo.test;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mycompany.demo.dto.User;
import com.mycompany.demo.entity.UserModel;
import com.mycompany.demo.exception.UserAlreadyExistsException;
import com.mycompany.demo.exception.UserBlackListedException;
import com.mycompany.demo.repository.UserRepository;
import com.mycompany.demo.service.ExclusionService;
import com.mycompany.demo.service.impl.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceTest extends DataPreparator {

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserRepository<User> userRepository;

	private ExclusionService exclusionService = Mockito.mock(ExclusionService.class);

	@Test
	public void registerUserTest() throws Exception {
		User request = prepareTO();
		UserModel mockEntity = prepareMockEntity(request);
		
		Mockito.when(exclusionService.validate(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		Mockito.when(userRepository.save(Mockito.any(UserModel.class))).thenReturn(mockEntity);

		User reponse = userService.registerUser(request);
		assertNotNull(reponse);
	}

	@Test(expected = UserBlackListedException.class)
	public void registerBlackListedUserTest() throws Exception {
		User request = prepareTO("someone.fraud");
		Mockito.when(exclusionService.validate("someone.fraud", "social-sec-number")).thenReturn(false);
		userService.registerUser(request);
	}
	
	@Test(expected = UserAlreadyExistsException.class)
	public void registerExistingUserTest() throws Exception {
		User request = prepareTO("existing.usre");
		Mockito.when(exclusionService.validate(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		Mockito.when(userRepository.save(Mockito.any(UserModel.class))).thenThrow(DataIntegrityViolationException.class);
		userService.registerUser(request);
	}

	@Test
	public void listAllUsersTest() {
		List<UserModel> mockResponse = Arrays.asList(prepareMockEntity(prepareTO()));
		Mockito.when(userRepository.findAll()).thenReturn(mockResponse);
		
		List<User> reponse = userService.listAllUser();
		assertNotNull(reponse);
	}

}

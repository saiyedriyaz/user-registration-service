package com.mycompany.demo.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mycompany.demo.constant.CommonCodes;
import com.mycompany.demo.controller.UserRegistrationResource;
import com.mycompany.demo.dto.User;
import com.mycompany.demo.exception.UserAlreadyExistsException;
import com.mycompany.demo.exception.UserBlackListedException;
import com.mycompany.demo.service.ExclusionService;
import com.mycompany.demo.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestLayerTest extends DataPreparator {

	@InjectMocks
	private UserRegistrationResource userRegistrationResource;

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private UserService userService;// = spy(UserServiceImpl.class);

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(this.userRegistrationResource).build();
	}

	private ExclusionService exclusionService = Mockito.mock(ExclusionService.class);

	@Test
	public void registerUserTest() throws Exception {
		User user = prepareTO("saiyedriyaz");

		Mockito.when(exclusionService.validate(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		Mockito.when(userService.registerUser(Mockito.any(User.class))).thenReturn(user);

		this.mockMvc.perform(
				post("/user/service/register").contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(user)))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void registerUserExceptionTest1() throws Exception {
		User user = prepareTO("saiyedriyaz");

		Mockito.when(exclusionService.validate(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		
		Mockito.when(userService.registerUser(Mockito.any(User.class))).thenThrow(UserAlreadyExistsException.class);

		this.mockMvc
				.perform(post("/user/service/register").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(mapToJson(user)))
				.andDo(print())
				.andExpect(content().string(containsString(CommonCodes.ERROR_CODE.USER_EXISTS.getCode())));
	}

	@Test
	public void registerUserExceptionTest2() throws Exception {
		User user = prepareTO("saiyedriyaz");

		Mockito.when(exclusionService.validate(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		Mockito.when(userService.registerUser(Mockito.any(User.class))).thenThrow(UserBlackListedException.class);

		this.mockMvc
				.perform(post("/user/service/register").contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(mapToJson(user)))
				.andDo(print())
				.andExpect(content().string(containsString(CommonCodes.ERROR_CODE.USER_BLACKLISTED.getCode())));
	}
	
	@Test
	public void registerBlackListedUserTest() throws Exception {
		Mockito.when(exclusionService.validate("someone.fraud", "social-sec-number")).thenReturn(false);
		this.mockMvc.perform(post("/user/service/register")).andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void listAllUsersTest() throws Exception {
		List<User> listAllResponse = Arrays.asList(prepareTO());
		Mockito.when(userService.listAllUser()).thenReturn(listAllResponse);

		this.mockMvc.perform(get("/user/service/listall")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("saiyedriyaz")));
	}
	
}

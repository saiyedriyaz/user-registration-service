package com.mycompany.demo.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.mycompany.demo.dto.User;
import com.mycompany.demo.validator.UserValidator;

@RunWith(SpringJUnit4ClassRunner.class)
public class ValidatorTest extends DataPreparator {

	private UserValidator validatorTest = new UserValidator();
	
	@Test
	public void validUserTest() {
		assertTrue(validatorTest.isValidUserName("saiyedriyaz"));
	}

	@Test
	public void inValidUserTest() {
		assertFalse(validatorTest.isValidUserName("saiyed riyaz"));
	}
	
	@Test
	public void inValidUserLengthTest() {
		assertFalse(validatorTest.isValidUserName("sai"));
	}

	@Test
	public void validPasswordTest() {
		assertTrue(validatorTest.isValidPassword("Pas123"));
	}

	@Test
	public void inValidPasswordTest1() {
		assertFalse(validatorTest.isValidPassword("Wrongpass1 2"));
	}
	
	@Test
	public void inValidPasswordTest2() {
		assertFalse(validatorTest.isValidPassword("Wr2"));
	}
	
	@Test
	public void inValidPasswordTest3() {
		assertFalse(validatorTest.isValidPassword("wrongpass1"));
	}
	
	@Test
	public void inValidPasswordTest4() {
		assertFalse(validatorTest.isValidPassword("Wrongpass"));
	}
	
	@Test
	public void validDateTest() {
		assertTrue(validatorTest.isValidDate("1985-10-11"));
	}
	
	@Test
	public void validSupportedClass() {
		assertTrue(validatorTest.supports(User.class));
	}

	@Test
	public void inValidDateTest() {
		//ValidationErrors validationErrors = Mockito.spy(ValidationErrors.class);
		User user = prepareTO("invalid user", "invalid pass", "1-1-1988");
		Errors errors = new BeanPropertyBindingResult(user, "");
		
		validatorTest.validate(user, errors);
		
		assertTrue(!errors.getAllErrors().isEmpty());
	}

	
}

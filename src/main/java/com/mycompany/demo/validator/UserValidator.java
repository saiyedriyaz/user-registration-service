package com.mycompany.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mycompany.demo.dto.User;
import com.mycompany.demo.util.DateUtil;

@Component()
public class UserValidator implements Validator {

	private static final String ATLEAST_ONE_UPPERCASE = "(.*[A-Z].*)";

	private static final String ATLEAST_ONE_NUMBER = "(.*[0-9].*)";

	private static final String ATLEAST_ONE_SPL_CHAR = "(.*[,~,!,@,#,$,%,^,&,*,(,),-,_,=,+,[,{,],},|,;,:,<,>,/,?,( )].*$)";

	private static final String ATLEAST_ONE_SPACE = "(.*( ).*)";

	public static final String INVALID_USER_MSG = "No space, dot or hiphen allowed in user name";

	public static final String INVALID_PASSWORD_MSG = "Password must be atleast four characters in lenght, must containg at least one upper case character, and atleast one number";

	public static final String INVALID_DATE_MSG = "Date of birth must follow yyyy-MM-dd format (ISO 8601 standard without timestamp)";

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;

		if (!isValidUserName(user.getUserName())) {
			errors.rejectValue("userName", "invalid.userName", INVALID_USER_MSG);
		}

		if (!isValidPassword(user.getPassword())) {
			errors.rejectValue("password", "invalid.password", INVALID_PASSWORD_MSG);
		}

		if (!isValidDate(user.getDateOfBirth())) {
			errors.rejectValue("dateOfBirth", "invalid.date.format", INVALID_DATE_MSG);
		}
	}

	/**
	 * username (alphanumerical, no spaces) . and - will be allowed in user name
	 */
	public boolean isValidUserName(String userName) {
		boolean valid = true;
		if (userName.length() < 4) {
			valid = false;
		}

		String specialChars = ATLEAST_ONE_SPL_CHAR;
		if (userName.matches(specialChars)) {
			valid = false;
		}

		return valid;
	}

	/**
	 * password (at least four characters, at least one upper case character, at
	 * least one number) no space allowed in password
	 * 
	 */
	public boolean isValidPassword(String password) {
		boolean valid = true;
		if (password.length() < 4) {
			valid = false;
		}
		String upperCaseChars = ATLEAST_ONE_UPPERCASE;
		if (valid && !password.matches(upperCaseChars)) {
			valid = false;
		}

		String numbers = ATLEAST_ONE_NUMBER;
		if (valid && !password.matches(numbers)) {
			valid = false;
		}

		String space = ATLEAST_ONE_SPACE;
		if (valid && password.matches(space)) {
			valid = false;
		}

		return valid;
	}

	public boolean isValidDate(String dateString) {
		return DateUtil.getDate(dateString) != null;
	}

	/*public static void main(String[] args) {
		// System.err.println(new UserValidator().validatePassword("Test 1"));
		System.err.println(new UserValidator().isValidDate("1981-15-12"));
	}*/

}

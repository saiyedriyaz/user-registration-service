package com.mycompany.demo.constant;

public interface CommonCodes {

	/**
	 * The Enum ERROR_CODE.
	 */
	public static enum ERROR_CODE {

	USER_EXISTS("USER_EXITS", "User with specified user name and/or SSN already exists."),

	USER_BLACKLISTED("USER_BLACKLISTED", "User is black listed.");

		private String code;

		private String message;

		/**
		 * Enum Constructor.
		 *
		 * @param errorCode the error code
		 */
		private ERROR_CODE(String errorCode, String errorMessage) {
			code = errorCode;

			// TODO - message to be read from property file
			message = errorMessage;
		}

		/**
		 * Gets the code.
		 *
		 * @return the code
		 */
		public String getCode() {
			return code;
		}

		/**
		 * Gets the message.
		 *
		 * @return the message
		 */
		public String getMessage() {
			return message;
		}
	}

}

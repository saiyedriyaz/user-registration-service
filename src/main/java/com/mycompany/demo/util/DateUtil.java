package com.mycompany.demo.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static final String ISO_8601_WITHOT_TIME = "yyyy-MM-dd";

	private static final DateFormat DOB_FORMAT = new SimpleDateFormat(DateUtil.ISO_8601_WITHOT_TIME);

	static {
		DOB_FORMAT.setLenient(false);
	}

	private DateUtil() {

	}

	public static Date getDate(String dateString) {
		try {
			return DOB_FORMAT.parse(dateString);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String getDate(Date dateObject) {
		return DOB_FORMAT.format(dateObject);
	}
}

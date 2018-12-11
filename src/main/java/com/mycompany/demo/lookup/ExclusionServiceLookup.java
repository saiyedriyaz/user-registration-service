package com.mycompany.demo.lookup;

import com.mycompany.demo.service.ExclusionService;

public class ExclusionServiceLookup {

	public static ExclusionService getExclusionServiceInstance() {
		return new ExclusionService() {

			@Override
			public boolean validate(String dob, String ssn) {
				return true;
			}
		};
	}
}

package com.mycompany.demo.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mycompany.demo.UserRegistrationApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserRegistrationApplication.class)
@WebAppConfiguration
public class UserRegistrationApplicationTest {

	@Test
	public void contextLoads() {
		UserRegistrationApplication.main(new String[] {});
	}

}

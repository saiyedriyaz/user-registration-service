package com.mycompany.demo.dto;

import javax.validation.constraints.NotBlank;

import com.mycompany.demo.entity.UserModel;
import com.mycompany.demo.util.DateUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class User {

	private Long id;

	@NotBlank
	private String userName;

	@NotBlank
	private String password;

	@NotBlank
	private String dateOfBirth;

	@NotBlank
	private String socialSecurityNumber;

	public User(UserModel userModel) {
		this.id = userModel.getId();
		this.userName = userModel.getUserName();
		this.password = userModel.getPassword();
		this.socialSecurityNumber = userModel.getSocialSecurityNumber();
		this.dateOfBirth = DateUtil.getDate(userModel.getDateOfBirth());
	}
}

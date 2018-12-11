package com.mycompany.demo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.mycompany.demo.dto.User;
import com.mycompany.demo.util.DateUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Table(uniqueConstraints={
		@UniqueConstraint(columnNames = {"userName"}),
		@UniqueConstraint(columnNames = {"socialSecurityNumber"})
		})
@Entity
@Getter @Setter @NoArgsConstructor @ToString
public class UserModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	private String userName;

	private String socialSecurityNumber;

	private String password;

	private Date dateOfBirth;

	public UserModel(User userTO) {
		setUserName(userTO.getUserName());
		setPassword(userTO.getPassword());
		setSocialSecurityNumber(userTO.getSocialSecurityNumber());
		setDateOfBirth(DateUtil.getDate(userTO.getDateOfBirth()));
	}
}

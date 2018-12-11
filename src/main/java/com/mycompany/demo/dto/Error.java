package com.mycompany.demo.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Error implements Serializable {

	private static final long serialVersionUID = 1L;

	private String errorCode;

	private String errorDescription;
}

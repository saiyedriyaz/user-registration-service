package com.mycompany.demo.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter @Setter @ToString
public class BaseResponse implements Serializable {

    private static final long serialVersionUID = 1l;

    /** The code. */
    private String code;

    /** The status. */
    private int status;

    /** The errors. */
    private Error error;
    
    private Date timestamp;
    
    private User user;
    
    private List<User> allUsers;
}

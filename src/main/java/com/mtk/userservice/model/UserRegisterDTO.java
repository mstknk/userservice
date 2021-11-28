package com.mtk.userservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserRegisterDTO {

	@NotBlank(message = "Name is mandatory")
	private String name;

	@NotBlank(message = "email is mandatory")
	@Email
	private String email;

	@NotBlank(message = "password is mandatory")
	private String password;

}

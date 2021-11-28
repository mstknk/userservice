package com.mtk.userservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserChangePasswordDTO {

	@NotBlank(message = "email is mandatory")
	@Email
	private String email;

	@NotBlank(message = "old password is mandatory")
	private String oldPassword;

	@NotBlank(message = "new password is mandatory")
	private String newPassword;

}

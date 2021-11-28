package com.mtk.userservice.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User Already Exist")
public class UserAlreadyRegister extends Exception {

}

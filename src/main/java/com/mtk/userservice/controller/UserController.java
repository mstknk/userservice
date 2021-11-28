package com.mtk.userservice.controller;

import com.mtk.userservice.error.InvalidCredentials;
import com.mtk.userservice.error.UserAlreadyRegister;
import com.mtk.userservice.error.UserNotFound;
import com.mtk.userservice.model.UserChangePasswordDTO;
import com.mtk.userservice.model.UserDTO;
import com.mtk.userservice.model.UserDetailDTO;
import com.mtk.userservice.model.UserRegisterDTO;
import com.mtk.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/user/register")
	public ResponseEntity<UserDetailDTO> register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) throws UserAlreadyRegister {
		return ResponseEntity.ok(userService.userRegister(userRegisterDTO));
	}

	@PostMapping("/user/login")
	public ResponseEntity<UserDetailDTO> login(@RequestBody @Valid UserDTO userDTO) throws UserNotFound, InvalidCredentials {
		return ResponseEntity.ok(userService.login(userDTO));
	}

	@DeleteMapping("/user")
	public ResponseEntity<String> delete(@RequestBody @Valid UserDTO userDTO) throws UserNotFound, InvalidCredentials {
		userService.delete(userDTO);
		return ResponseEntity.ok("User is deleted successfully.");
	}

	@PostMapping("/user/changepassword")
	public ResponseEntity<String> changePassword(@RequestBody @Valid UserChangePasswordDTO userChangePasswordDTO) throws UserNotFound, InvalidCredentials {
		userService.changePassword(userChangePasswordDTO);
		return ResponseEntity.ok("User changed password successfully.");
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put("message", errorMessage);
			errors.put("field", fieldName);
		});
		return errors;
	}
}

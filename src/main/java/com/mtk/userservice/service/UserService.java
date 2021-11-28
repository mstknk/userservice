package com.mtk.userservice.service;

import com.mtk.userservice.dao.UserLoginHistoryRepository;
import com.mtk.userservice.dao.UserRepository;
import com.mtk.userservice.db.User;
import com.mtk.userservice.db.UserLoginHistory;
import com.mtk.userservice.error.InvalidCredentials;
import com.mtk.userservice.error.UserAlreadyRegister;
import com.mtk.userservice.error.UserNotFound;
import com.mtk.userservice.model.UserChangePasswordDTO;
import com.mtk.userservice.model.UserDTO;
import com.mtk.userservice.model.UserDetailDTO;
import com.mtk.userservice.model.UserRegisterDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserLoginHistoryRepository userLoginHistoryRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;

	public UserDetailDTO userRegister(UserRegisterDTO userRegisterDTO) throws UserAlreadyRegister {
		User userDB = userRepository.findUserByEmail(userRegisterDTO.getEmail());
		if (userDB != null) {
			throw new UserAlreadyRegister();
		}

		User user = modelMapper.map(userRegisterDTO, User.class);
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		userDB = userRepository.save(user);
		logUserLogin(userDB.getId(), true);

		return modelMapper.map(userDB, UserDetailDTO.class);
	}

	public UserDetailDTO login(UserDTO userDTO) throws UserNotFound, InvalidCredentials {
		User userDB = userRepository.findUserByEmail(userDTO.getEmail());
		if (userDB == null) {
			throw new UserNotFound();
		}
		if (passwordEncoder.matches(userDTO.getPassword(), userDB.getPassword())) {
			logUserLogin(userDB.getId(), true);
			return modelMapper.map(userDB, UserDetailDTO.class);
		}
		logUserLogin(userDB.getId(), false);

		throw new InvalidCredentials();

	}

	public void delete(UserDTO userDTO) throws UserNotFound, InvalidCredentials {

		User userDB = userRepository.findUserByEmail(userDTO.getEmail());
		if (userDB == null) {
			throw new UserNotFound();
		}
		if (passwordEncoder.matches(userDTO.getPassword(), userDB.getPassword())) {
			userRepository.delete(userDB);
		} else {
			throw new InvalidCredentials();
		}
	}

	public void changePassword(UserChangePasswordDTO userChangePasswordDTO) throws UserNotFound, InvalidCredentials {
		User userDB = userRepository.findUserByEmail(userChangePasswordDTO.getEmail());
		if (userDB == null) {
			throw new UserNotFound();
		}
		if (passwordEncoder.matches(userChangePasswordDTO.getOldPassword(), userDB.getPassword())) {
			userDB.setPassword(passwordEncoder.encode(userChangePasswordDTO.getNewPassword()));
			userRepository.save(userDB);
		} else {
			throw new InvalidCredentials();
		}
	}

	private void logUserLogin(Long userId, boolean successfulLogin) {
		UserLoginHistory userLoginHistory = new UserLoginHistory();
		userLoginHistory.setUserId(userId);
		userLoginHistory.setSuccessful(successfulLogin);
		userLoginHistoryRepository.save(userLoginHistory);
	}
}

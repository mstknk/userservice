package com.mtk.userservice.dao;

import com.mtk.userservice.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findUserByEmail(String email);

}

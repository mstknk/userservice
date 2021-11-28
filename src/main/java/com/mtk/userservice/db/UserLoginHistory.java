package com.mtk.userservice.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "userLoginHistory")
@Getter
@Setter
public class UserLoginHistory implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "userId")
	private Long userId;

	private Date loginDate;
	private boolean successful;

	@PrePersist
	protected void onCreate() {
		loginDate = new Date();
	}

}

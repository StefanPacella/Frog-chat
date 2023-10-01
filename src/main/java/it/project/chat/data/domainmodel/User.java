package it.project.chat.data.domainmodel;

import java.util.List;
import java.util.Optional;

import it.project.chat.framework.data.BusinessException;
import it.project.chat.framework.data.DataItem;

public abstract class User implements DataItem {

	private Integer id;

	private String nickname;

	private String password;

	private String email;

	private String picture;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public abstract Optional<List<User>> listContact() throws BusinessException;


}

package com.company.airline.entity;

import com.company.airline.dao.UserDao;
import com.company.airline.dao.factory.DaoFactoryInstance;
import com.company.airline.exception.DaoException;

public class UserBuilder {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String role;
	
	public String getFirstName() {
		return firstName;
	}

	public UserBuilder setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public UserBuilder setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public UserBuilder setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public UserBuilder setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getRole() {
		return role;
	}

	public UserBuilder setRole(String role) {
		this.role = role;
		return this;
	}

	@Override
	public String toString() {
		return lastName + " " + firstName + " " + email + " " + role;
	}
	
	public User create() throws DaoException {
		UserDao dao = DaoFactoryInstance.getFactory().getUserDao();
		return dao.createUser(this);
	}
}

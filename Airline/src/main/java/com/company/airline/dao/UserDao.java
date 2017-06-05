package com.company.airline.dao;

import java.util.Date;
import java.util.List;

import com.company.airline.entity.User;
import com.company.airline.entity.UserBuilder;
import com.company.airline.exception.DaoException;

public interface UserDao {
	
	public User createUser(UserBuilder builder) throws DaoException;
	public User findUserByEmail(String email) throws DaoException;
	public List<User> findUsersForCrewExceptDate(Date date) throws DaoException;
	public List<User> findUsersByFlightId(Long id) throws DaoException;
	public List<User> findAllUsersExceptAdmin() throws DaoException;
	public User getUserById(long id) throws DaoException;
	public void updateUserRoleById(long id, String roleName) throws DaoException;
}

package com.company.airline.dao;

import java.util.Date;
import java.util.List;

import com.company.airline.entity.User;
import com.company.airline.entity.UserBuilder;
import com.company.airline.exception.DaoException;

public interface UserDao {
	
	/**
	 * @return created user with auto-generated id
	 */
	public User createUser(UserBuilder builder) throws DaoException;
	
	/**
	 * @return user WITHOUT role (role = null) or null if user with given email is not exist
	 */
	public User findUserByEmail(String email) throws DaoException;
	
	/**
	 * Users for crew: pilot, navigator, radioman, stewardess
	 * @return users or empty List if users with given conditions are not exist
	 */
	public List<User> findUsersForCrewExceptDate(Date date) throws DaoException;
	
	/**
	 * @return users or empty List if users with given conditions are not exist
	 */
	public List<User> findUsersByFlightId(Long id) throws DaoException;
	
	public List<User> findAllUsersExceptAdmin() throws DaoException;
	
	public User getUserById(long id) throws DaoException;
	
	public void updateUserRoleById(long id, String roleName) throws DaoException;
	
}

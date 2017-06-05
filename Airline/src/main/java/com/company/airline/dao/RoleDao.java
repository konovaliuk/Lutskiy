package com.company.airline.dao;

import com.company.airline.entity.Role;
import com.company.airline.exception.DaoException;

public interface RoleDao {
	public Role getRoleByName(String roleName) throws DaoException;
	public Role getRoleById(int id) throws DaoException;
	public Role getRoleByUserId(long id) throws DaoException;
}

/**
 * 
 */
package edu.sjsu.cmpe275.lab2.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.lab2.model.Phone;
import edu.sjsu.cmpe275.lab2.model.User;

/**
 * @author Onkar Ganjewar
 * Cmpe275-Lab2
 */
@Service
public class UserServiceImpl {

	@Autowired
	private edu.sjsu.cmpe275.lab2.dao.UserDAO UserDAO;
	
	/**
	 * Default constructor
	 */
	public UserServiceImpl() {
	}
	
	
	@Transactional
	public void add(User user) {
		UserDAO.insert(user);	
	}

	@Transactional
	public void modify(User user) {
		UserDAO.update(user); 
	}

	@Transactional
	public void delete(Integer id) {
		UserDAO.deleteById(id); 
	}

	@Transactional
	public User getUser(Integer id) {
		return UserDAO.findById(id);
	}

	@Transactional
	public List<User> getAllUsers() {
		return UserDAO.findAllUsers();
	}

	/**
	 * Check whether the user already exists in database
	 * @param id
	 * 			Id of the user to be searched.
	 * @return True, if user already existed.
	 */
	public boolean userExists(Integer id) {
		return UserDAO.exists(id);
	}

	@Transactional
	public List<Phone> findAllPhones(Integer id) {
		return UserDAO.getAllPhones(id);
	}
}

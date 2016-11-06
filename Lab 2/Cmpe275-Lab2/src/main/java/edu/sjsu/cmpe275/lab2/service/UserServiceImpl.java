/**
 * 
 */
package edu.sjsu.cmpe275.lab2.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.lab2.dao.UserDAOImpl;
import edu.sjsu.cmpe275.lab2.model.User;

/**
 * @author Onkar Ganjewar
 * Cmpe275-Lab2
 */
@Service
public class UserServiceImpl {

	@Autowired
	private UserDAOImpl UserDAO;
	
	@Transactional
	public void add(User user) {
		// TODO Auto-generated method stub
		UserDAO.insert(user);	
	}

	@Transactional
	public void edit(User user) {
		// TODO Auto-generated method stub
		UserDAO.update(user); 
	}

	@Transactional
	public void delete(String id) {
		// TODO Auto-generated method stub
		UserDAO.deleteById(id); 
	}

	@Transactional
	public User getUser(String id) {
		// TODO Auto-generated method stub
		return UserDAO.findById(id);
	}

	@Transactional
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return UserDAO.findAllUsers();
	}
}

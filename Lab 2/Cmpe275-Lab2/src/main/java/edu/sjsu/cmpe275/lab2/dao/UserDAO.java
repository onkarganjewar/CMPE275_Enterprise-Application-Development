/**
 * 
 */
package edu.sjsu.cmpe275.lab2.dao;

import java.util.List;

import edu.sjsu.cmpe275.lab2.model.User;

/**
 * @author Onkar Ganjewar
 * Cmpe275-Lab2
 */
public interface UserDAO {

	public User findById(String id);
	
	public User findByPhone(long phone);
	
	public void update(User user);
	
	public void insert(User user);
	
	public void deleteById(String id);
	
	public List<User> findAllUsers();

}

/**
 * 
 */
package edu.sjsu.cmpe275.lab2.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import edu.sjsu.cmpe275.lab2.model.User;

/**
 * @author Onkar Ganjewar
 * Cmpe275-Lab2
 */
@Repository
public interface UserDAO {

	public User findById(Integer id);
	
	public User findByPhone(long phone);
	
	public void update(User user);
	
	public void insert(User user);
	
	public void deleteById(Integer id);
	
	public List<User> findAllUsers();

	public boolean exists(Integer id);
}

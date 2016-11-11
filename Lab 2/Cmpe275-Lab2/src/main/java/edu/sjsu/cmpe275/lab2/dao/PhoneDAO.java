/**
 * 
 */
package edu.sjsu.cmpe275.lab2.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import edu.sjsu.cmpe275.lab2.model.Phone;
import edu.sjsu.cmpe275.lab2.model.User;

/**
 * @author Onkar Ganjewar
 * Cmpe275-Lab2
 */
@Repository
public interface PhoneDAO {

		public Integer getPhoneId(Phone phone);
	
		public Phone findById(Integer id);
		
		public void update(Phone phone);
		
		public void insert(Phone phone);
		
		public void deleteById(Integer id);
		
		public List<Phone> findAllPhones();

		public List<User> getAllUsers(Integer id);

		public boolean exists(Integer id);

}

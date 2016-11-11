package edu.sjsu.cmpe275.lab2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.lab2.dao.PhoneDAO;
import edu.sjsu.cmpe275.lab2.model.Phone;
import edu.sjsu.cmpe275.lab2.model.User;

/**
 * @author Onkar Ganjewar
 * Cmpe275-Lab2
 */

@Service
public class PhoneService {
	@Autowired
	private PhoneDAO phoneDAO;
	/**
	 * Default constructor
	 */
	public PhoneService() {
	}
	
	@Transactional
	public void add(Phone phone) {
		phoneDAO.insert(phone);	
	}

	@Transactional
	public void modify(Phone phone) {
		phoneDAO.update(phone); 
	}

	@Transactional
	public void delete(Integer id) {
		phoneDAO.deleteById(id); 
	}

	@Transactional
	public Phone getPhone(Integer id) {
		return phoneDAO.findById(id);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Integer getPhoneId(Phone phone) {
		return phoneDAO.getPhoneId(phone);
	}
	
	@Transactional
	public List<Phone> getAllPhones() {
		return phoneDAO.findAllPhones();
	}

	@Transactional
	public List<User> findAllUsers(Integer id) {
		return phoneDAO.getAllUsers(id);
	}

	@Transactional
	public boolean phoneExists(Integer id) {
		return phoneDAO.exists(id);
	}

}

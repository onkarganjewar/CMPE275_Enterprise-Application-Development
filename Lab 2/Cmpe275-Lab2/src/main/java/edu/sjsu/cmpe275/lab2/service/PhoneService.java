package edu.sjsu.cmpe275.lab2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.lab2.dao.PhoneDAO;
import edu.sjsu.cmpe275.lab2.model.Phone;

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

	@Transactional
	public Integer getKey(Phone phone) throws Exception {
		return phoneDAO.getPhoneKey(phone);
	}
	
	@Transactional
	public List<Phone> getAllPhones() {
		return phoneDAO.findAllPhones();
	}

}

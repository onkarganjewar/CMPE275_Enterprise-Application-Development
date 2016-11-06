/**
 * 
 */
package edu.sjsu.cmpe275.lab2.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import edu.sjsu.cmpe275.lab2.model.Phone;
import edu.sjsu.cmpe275.lab2.model.User;

/**
 * @author Onkar Ganjewar
 * Cmpe275-Lab2
 */
//@Service
public class UserService {
	private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(UserService.class.getName());

	public static void main(String[] args) {
//		User user1 = new User();
//		user1.setFirstName("a");
//		user1.setEmail("email");
//		user1.setLastName("lasd");
//		user1.setTitle("titel");
//		Phone phone1 = new Phone();
//		phone1.setNumber("123123");
//		phone1.setDescription("desc");
//		
//		List<Phone> phones = new ArrayList<Phone>();
//		phones.add(phone1);
//		logger.info("********************************STARTED");
//		user1.setPhones(phones);
//		
//		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");
//		
//		EntityManager entityManager = entityManagerFactory.createEntityManager();
//		logger.info("********************************DO");
//		
//		EntityTransaction entityTransaction = null;
//		
//		try {
//			logger.info("********************************CATCH");
//				entityTransaction = entityManager.getTransaction();
//			entityTransaction.begin();
//			entityManager.persist(user1);
//			entityTransaction.commit();
//		} catch (PersistenceException e) {
//			// TODO: handle exception
//			logger.info("********************************TRY");
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
	}
}

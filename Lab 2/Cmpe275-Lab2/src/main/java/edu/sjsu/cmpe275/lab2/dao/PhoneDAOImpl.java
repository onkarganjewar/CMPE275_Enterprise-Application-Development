package edu.sjsu.cmpe275.lab2.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.lab2.model.Phone;
import edu.sjsu.cmpe275.lab2.model.User;

/**
 * @author Onkar Ganjewar Cmpe275-Lab2
 */
@Repository
public class PhoneDAOImpl implements PhoneDAO {

	// Injected database connection:
	@PersistenceContext
	protected EntityManager em;

	@Transactional
	public Phone findById(Integer id) throws DataAccessException {
		return em.find(Phone.class, id);
	}

	@Transactional
	public void update(Phone phone) throws DataAccessException {
		em.merge(phone);
	}

	@Transactional
	public void insert(Phone phone) throws DataAccessException {
		try {
			em.persist(phone);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void deleteById(Integer id) {
		Phone entity = new Phone();
		entity.setId(id);
		System.out.println("PhoneService::Delete called for id:" + id);
		try {
			em.remove(em.contains(entity) ? entity : em.merge(entity));
		} catch (Throwable e) {
			System.out.println("Exception caught");
			e.printStackTrace();
		}
	}

	@Transactional
	public List<Phone> 	findAllPhones() throws DataAccessException {
		Query query = em.createQuery("SELECT p FROM Phone p ORDER BY p.id");
		@SuppressWarnings("unchecked")
		List<Phone> resultList = query.getResultList();
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Integer getPhoneId(Phone phone) {
		return phone.getId();
	}

	/**
	 * Fetches the list of all the users assigned to the given phone id
	 */
	@Transactional
	public List<User> getAllUsers(Integer id) {

		List<User> userList = new ArrayList<User>();
		Query query2 = em.createQuery("Select m from User m INNER JOIN m.phones t where t.id=:arg1");
		query2.setParameter("arg1", id);

		try {
			userList = (List<User>) query2.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		return userList;
	}

	@Transactional
	public boolean exists(Integer id) {
		Phone pr = new Phone();
		pr.setId(id);
		return em.find(Phone.class, id) != null;
	}
}

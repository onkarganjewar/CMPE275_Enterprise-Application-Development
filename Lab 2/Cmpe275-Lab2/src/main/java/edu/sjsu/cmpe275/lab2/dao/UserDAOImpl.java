
package edu.sjsu.cmpe275.lab2.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import edu.sjsu.cmpe275.lab2.model.User;

/**
 * @author Onkar Ganjewar Cmpe275-Lab2
 */

@Repository
public class UserDAOImpl implements UserDAO {

	// Injected database connection:
	@PersistenceContext
	protected EntityManager em;

//	public EntityManager getEntityManager() {
//		return em;
//	}
//
//	public void setEntityManager(EntityManager entityManager) {
//		this.em = entityManager;
//	}

	@Transactional
	public User getSpecificUser(String id) {
		User u;
		Query query = em.createQuery("Select u from User u where u.id=:arg1");
		query.setParameter("arg1", Integer.parseInt(id));
		try {
			u = (User) query.getSingleResult();
		} catch (NoResultException e) {
			u = null;
		}
		return u;
	}

	@Transactional
	public List<User> findAllUsers() throws DataAccessException {
		Query query = em.createQuery("SELECT u FROM User u ORDER BY u.id");
		List<User> resultList = query.getResultList();
		return resultList;
	}

	@Transactional
	public User findById(String id) throws DataAccessException {
		return em.find(User.class, id);
	}


	@Transactional
	public User findByPhone(long phone) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteById(String id) {
	
	}
	
	@Transactional
	public void update(User user) {
		// TODO Auto-generated method stub
		em.merge(user);
	}

	@Transactional
	public void insert(User user) {
		// TODO Auto-generated method stub
		em.persist(user);
	}


}
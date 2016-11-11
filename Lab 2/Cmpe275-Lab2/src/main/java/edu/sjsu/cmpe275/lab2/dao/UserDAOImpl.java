
package edu.sjsu.cmpe275.lab2.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.lab2.model.Phone;
import edu.sjsu.cmpe275.lab2.model.User;

/**
 * @author Onkar Ganjewar Cmpe275-Lab2
 */

@Repository
public class UserDAOImpl implements UserDAO {

	// Injected database connection:
	@PersistenceContext
	protected EntityManager em;

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
		@SuppressWarnings("unchecked")
		List<User> resultList = query.getResultList();
		return resultList;
	}

	@Transactional
	public User findById(Integer id) throws DataAccessException {
		return em.find(User.class, id);
	}

	@Transactional
	public void deleteById(Integer id) {
		User entity = new User();
		entity.setuserId(id);
		System.out.println("UserService::Delete called for id:" + id);
		em.remove(em.contains(entity) ? entity : em.merge(entity));
	}

	/**
	 * will check if the user with the id exists?
	 * 
	 * @param id
	 *            of the user
	 * @return yes if it exists else no
	 */
	@Transactional
	public boolean exists(Integer id) {
		User pr = new User();
		pr.setuserId(id);
		return em.find(User.class, id) != null;
	}

	@Transactional
	public void update(User user) {
		em.merge(user);
	}

	@Transactional
	public void insert(User user) {
		em.persist(user);
	}

	/**
	 * Fetch the list of all the phones assigned to the given user
	 */
	@Transactional
	public List<Phone> getAllPhones(Integer id) {

		List<Phone> phoneList = new ArrayList<Phone> ();
		Query query = em.createQuery("Select m from Phone m INNER JOIN m.listOfUsers t where t.id=:arg1");
		query.setParameter("arg1", id);
		
		try {
			phoneList = ((List<Phone>) query.getResultList());
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		return phoneList;
	}
}
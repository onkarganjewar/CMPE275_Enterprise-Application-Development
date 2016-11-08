package edu.sjsu.cmpe275.lab2.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.lab2.model.Phone;

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

	// @Transactional
	// public Integer getPhoneKey(Phone phone) throws Exception{
	// Object id =
	// em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(phone);
	// return Integer.parseInt(id.toString());
	// }

	@Transactional
	public void insert(Phone phone) throws DataAccessException {
		em.persist(phone);
	}

	@Transactional
	public void deleteById(Integer id) throws DataAccessException {
		Phone entity = new Phone();
		entity.setPhoneId(id);
		System.out.println("PhoneService::Delete called for id:" + id);
		em.remove(em.contains(entity) ? entity : em.merge(entity));
	}

	@Transactional
	public List<Phone> findAllPhones() throws DataAccessException {
		Query query = em.createQuery("SELECT p FROM Phone p ORDER BY p.id");
		@SuppressWarnings("unchecked")
		List<Phone> resultList = query.getResultList();
		return resultList;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Integer getPhoneId(Phone phone) {
		return phone.getPhoneId();
	}
}

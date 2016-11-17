package edu.sjsu.cmpe275.lab2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Onkar Ganjewar Cmpe275-Lab2
 */
@Entity
@Table(name = "PHONE")
public class Phone implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "PHONE_ID")
	private Integer id;

	@Column(name = "PHONE_NUMBER", unique = true, nullable = false)
	private String number; // Note, phone numbers must be unique

	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	@Embedded
	private Address address;

	@ManyToMany(mappedBy = "phones")
	@JsonIgnoreProperties({"phones","address","title"})
	private List<User> users = new ArrayList<User>(); 

	public Phone() {
	}

	/**
	 * @param phoneId
	 * 			Id of the phone entity
	 * @param phoneNumber
	 * 			Phone number to be stored for the phone entity
	 * @param description
	 * 			Description of this phone entity
	 * @param address
	 * 			Embedded object address to
	 * @param listOfUsers
	 * 			List of users this phone is assigned to
	 */
	public Phone(Integer phoneId, String phoneNumber, String description, Address address, List<User> listOfUsers) {
		super();
		this.id = phoneId;
		this.number = phoneNumber;
		this.description = description;
		this.address = address;
		this.users = listOfUsers;
	}

	/**
	 * @return the listOfUsers
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * @param listOfUsers the listOfUsers to set
	 */
	public void setUsers(List<User> listOfUsers) {
		this.users = listOfUsers;
	}

	
	/**
	 * @return the phoneNumber
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public void setNumber(String phoneNumber) {
		this.number = phoneNumber;
	}

	/**
	 * @return the phoneId
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param phoneId
	 *            the phoneId to set
	 */
	public void setId(Integer phoneId) {
		this.id = phoneId;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Phone))
			return false;
		Phone other = (Phone) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Phone [id=" + id + ", Number=" + number + ", description=" + description + ", Owners =" + users.size()
				+ "]";
	}

}

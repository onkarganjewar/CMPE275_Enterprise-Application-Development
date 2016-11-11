/**
 * 
 */
package edu.sjsu.cmpe275.lab2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

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
	private Integer phoneId;

	// @NotEmpty
	@Column(name = "PHONE_NUMBER", nullable = false)
	private String phoneNumber; // Note, phone numbers must be unique

	// @NotEmpty
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	@Embedded
	private Address address;

//	@ManyToMany(mappedBy = "listOfPhones")
	
	@ManyToMany
	@JoinTable(name = "Users_Phones", 
	joinColumns = @JoinColumn(name = "PHONE_ID"), 
	inverseJoinColumns = @JoinColumn(name = "ID"))
	private List<User> listOfUsers = new ArrayList<User>(); 

	/**
	 * @return the listOfUsers
	 */
	public List<User> getListOfUsers() {
		return listOfUsers;
	}

	/**
	 * @param listOfUsers the listOfUsers to set
	 */
	public void setListOfUsers(List<User> listOfUsers) {
		this.listOfUsers = listOfUsers;
	}

	
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the phoneId
	 */
	public Integer getPhoneId() {
		return phoneId;
	}

	/**
	 * @param phoneId
	 *            the phoneId to set
	 */
	public void setPhoneId(Integer phoneId) {
		this.phoneId = phoneId;
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
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Phone))
			return false;
		Phone other = (Phone) obj;
		if (phoneId == null) {
			if (other.phoneId != null)
				return false;
		} else if (!phoneId.equals(other.phoneId))
			return false;
		return true;
	}

}

/**
 * 
 */
package edu.sjsu.cmpe275.lab2.model;


import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * @author Onkar Ganjewar Cmpe275-Lab2
 */
@Entity
@Table(name = "PHONE")
public class Phone {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private String phoneId;

//	@NotEmpty
	@Column(name="PHONE_NUMBER", nullable=false)
	private String number; // Note, phone numbers must be unique
	
//	@NotEmpty
	@Column(name="DESC", nullable=false)
	private String description;
	
	@Embedded
	private Address address;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OWNER_ID")
	private User owner;
	
//	@ManyToMany(mappedBy = "phones")
//	private List<User> users = new ArrayList<User> ();
	
	/**
	 * @return the phoneId
	 */
	public String getPhoneId() {
		return phoneId;
	}

	/**
	 * @param phoneId the phoneId to set
	 */
	public void setPhoneId(String phoneId) {
		this.phoneId = phoneId;
	}

	/**
	 * @return the phone number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
}

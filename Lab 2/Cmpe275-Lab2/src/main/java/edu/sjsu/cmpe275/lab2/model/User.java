/**
 *
 */
package edu.sjsu.cmpe275.lab2.model;

import java.io.Serializable; 
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Onkar Ganjewar Cmpe275-Lab2
 */
@Entity
@Table(name = "APP_USER")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Integer id;

	@Column(name = "TITLE", nullable = false)
	private String title;

	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;

	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;

	@Embedded
	private Address address;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "Users_Phones", 
	joinColumns = @JoinColumn(name = "ID"), 
	inverseJoinColumns = @JoinColumn(name = "PHONE_ID"))
	@JsonIgnoreProperties({"users","address"})
	private List<Phone> phones = new ArrayList<Phone>();

	public User() {

	}

	/**
	 * @param userId
	 * 			Id of the user entity
	 * @param title
	 * 			Title of the user to be stored
	 * @param firstName
	 * 			First name of the user 
	 * @param lastName
	 * 			Last name of the user
	 * @param address
	 * 			Embedded address object
	 * @param phones
	 * 			List of phones associated to this user
	 */
	public User(Integer userId, String title, String firstName, String lastName, Address address,
			List<Phone> phones) {
		super();
		this.id = userId;
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phones = phones;
	}

	/**
	 * @return the phones
	 */
	public List<Phone> getPhones() {
		return phones;
	}

	/**
	 * @param phones
	 *            the phones to set
	 */
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		final Integer prime = 31;
		Integer result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", title=" + title + ", firstName=" + firstName + ", lastName=" + lastName
				+ "]";
	}

}

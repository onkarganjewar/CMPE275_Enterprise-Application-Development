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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Onkar Ganjewar Cmpe275-Lab2
 */
@Entity
@Table(name = "APP_USER")
public class User implements Serializable {

	// private static final java.util.logging.Logger logger =
	// java.util.logging.Logger.getLogger(User.class.getName());
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

//	@GeneratedValue
	@Id
	@Column(name = "ID")
	private Integer userId;

	@Column(name = "TITLE", nullable = false)
	private String title;

	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;

	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;

	@Column(name = "EMAIL", nullable = false)
	private String email;

	// @NotEmpty
	// @OneToMany(fetch = FetchType.LAZY)
	// @JoinTable(name = "USER_PHONE",
	// joinColumns = { @JoinColumn(name = "USER_ID", referencedColumnName =
	// "ID") },
	// inverseJoinColumns = { @JoinColumn(name = "PHONE_ID",
	// referencedColumnName = "ID", unique = true) })
	// private List<Phone> phones = new ArrayList<Phone>();

	@Embedded
	private Address address;

	@OneToMany(mappedBy = "owner", cascade = CascadeType.MERGE)
	private List<Phone> phones = new ArrayList<Phone>();

	public User() {

	}

	/**
	 * @param userId
	 * @param title
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param address
	 * @param phones
	 */
	public User(Integer userId, String title, String firstName, String lastName, String email, Address address,
			List<Phone> phones) {
		super();
		this.userId = userId;
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
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

	public Integer getuserId() {
		return userId;
	}

	public void setuserId(Integer id) {
		this.userId = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		final Integer prime = 31;
		Integer result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + userId + ", title=" + title + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + "]";
	}

}

package edu.sjsu.cmpe275.lab2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sjsu.cmpe275.lab2.model.Address;
import edu.sjsu.cmpe275.lab2.model.Phone;
import edu.sjsu.cmpe275.lab2.model.User;
import edu.sjsu.cmpe275.lab2.service.PhoneService;
import edu.sjsu.cmpe275.lab2.service.UserServiceImpl;

/**
 * @author Onkar Ganjewar Cmpe275-Lab2
 */
@Controller
public class PhoneController {

	@Autowired
	private PhoneService phoneService;

	@Autowired
	private UserServiceImpl userService;

	/**
	 * Index page for phone entity.
	 * 
	 * @param model
	 * 
	 * @return HTML page to register a new phone entity
	 */

	@RequestMapping(value = "/phone", method = RequestMethod.GET)
	public String renderHome(Model model) {
		model.addAttribute("phone", new Phone());
		return "phoneIndex";
	}

	/**
	 * Function to generate random identification numbers for each user entity.
	 * 
	 * @return Random integer number
	 */
	private Integer randomIdGenerator() {
		Random rn = new Random();
		rn.setSeed(System.currentTimeMillis());
		while (true) {
			Integer rand_id = rn.nextInt(Integer.SIZE - 1) % 10000;
			if (!userService.userExists(rand_id)) {
				return rand_id;
			}
		}
	}

	/**
	 * Attaches the user to given phone entity.
	 * 
	 * @param request
	 *            Http request containing all the attributes of phone and user
	 *            entity
	 * @return Redirects to the HTML view that renders phone entity details
	 */
	@RequestMapping(value = "/phone/addUsers", method = RequestMethod.POST)
	public String insertUser(HttpServletRequest request) {
		// Instantiate the list of phones to be added in the user entity
		String phoneId = request.getParameter("phoneId");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String title = request.getParameter("title");
		String street = request.getParameter("street");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String zip = request.getParameter("zip");

		User user = new User();
		Integer userId = randomIdGenerator();
		user.setId(userId);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setTitle(title);

		Address address = new Address();
		address.setCity(city);
		address.setStreet(street);
		address.setState(state);
		address.setZip(zip);

		user.setAddress(address);
		userService.add(user);

		Phone phone = new Phone();
		phone = phoneService.getPhone(Integer.parseInt(phoneId));

		List<User> oldUsers = new ArrayList<User>();
		oldUsers = phoneService.findAllUsers(Integer.parseInt(phoneId));

		List<User> allUsers = new ArrayList<User>();
		allUsers.addAll(oldUsers);
		allUsers.add(user);

		phone.setUsers(allUsers);
		phoneService.modify(phone);

		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);

		user.setPhones(phones);

		userService.modify(user);
		// Redirect to the phone details HTML page
		return "redirect:/phone/" + phoneId;
	}

	/**
	 * Attaches existing user to the given phone entity
	 * 
	 * @param request
	 *            HttpServletRequest containing all the user parameters to be
	 *            added
	 * @return Redirects to the phone entity details view
	 */
	@RequestMapping(value = "/phone/insertUsers", method = RequestMethod.POST)
	public String attachUser(HttpServletRequest request) {
		// Instantiate the list of phones to be added in the user entity
		String phoneId = request.getParameter("phoneId");
		// String firstName = request.getParameter("firstName");
		// String lastName = request.getParameter("lastName");
		String userId = request.getParameter("userId");

		User user = new User();
		user = userService.getUser(Integer.parseInt(userId));

		Phone phone = new Phone();
		phone = phoneService.getPhone(Integer.parseInt(phoneId));

		List<User> oldUsers = new ArrayList<User>();
		oldUsers = phoneService.findAllUsers(Integer.parseInt(phoneId));

		List<User> allUsers = new ArrayList<User>();
		allUsers.addAll(oldUsers);
		allUsers.add(user);

		phone.setUsers(allUsers);
		phoneService.modify(phone);


		List<Phone> oldPhones = new ArrayList<Phone>();
		oldPhones = userService.findAllPhones(Integer.parseInt(userId));
		
		List<Phone> allPhones = new ArrayList<Phone>();
		allPhones.addAll(oldPhones);
		allPhones.add(phone);
		
		user.setPhones(allPhones);

		userService.modify(user);
		// Redirect to the phone details HTML page
		return "redirect:/phone/" + phoneId;
	}

	/**
	 * Detaches the particular user from the list of associated users to the
	 * phone entity.
	 * 
	 * @param request
	 *            HttpServletRequest containing all the parameter values.
	 * @return Redirects the user to the phone details HTML view
	 */

	@RequestMapping(value = "/phone/detachUser", method = RequestMethod.POST)
	public String detachUser(HttpServletRequest request) {

		String phoneId = request.getParameter("phoneId");
		Integer uId = Integer.parseInt(request.getParameter("userId"));

		User user = new User();
		user = userService.getUser(uId);
		Phone phone = new Phone();
		phone = phoneService.getPhone(Integer.parseInt(phoneId));

		List<User> oldUsers = new ArrayList<User>();
		List<User> newUsers = new ArrayList<User>();
		oldUsers = phoneService.findAllUsers(Integer.parseInt(phoneId));

		for (User u : oldUsers) {
			if (u.getId() != uId) {
				newUsers.add(u);
			}
			System.out.println(u);
		}

		phone.setUsers(newUsers);
		phoneService.modify(phone);

		List<Phone> newPhones = new ArrayList<Phone>();
		List<Phone> oldPhones = new ArrayList<Phone>();
		oldPhones = userService.findAllPhones(uId);

		for (Phone p : oldPhones) {
			Integer pId = p.getId();
			if (pId != Integer.parseInt(phoneId)) {
				newPhones.add(p);
			}
		}

		user.setPhones(newPhones);
		userService.modify(user);

		// Redirect to the phone details HTML page
		return "redirect:/phone/" + phoneId;
	}

	/**
	 * Creates the new phone entity in the database.
	 * 
	 * @param desc
	 *            Description of phone to be added
	 * @param userId
	 *            User Id of the user entity that owns this phone
	 * @param number
	 *            Phone number
	 * @param response
	 *            HTTP response to set response status codes
	 * @param request
	 *            Servlet request containing all the attributes of the phone
	 *            entity
	 * @return HTML view holding all the details of newly added phone
	 */
	@RequestMapping(value = "/test/addPhone", method = RequestMethod.POST)
	public @ResponseBody String createPhone(@RequestParam(value = "desc") String desc,
			@RequestParam(value = "userId") String userId, @RequestParam(value = "number") String number,
			@RequestParam("street") String street, @RequestParam("zip") String zip, @RequestParam("city") String city,
			@RequestParam("state") String state, HttpServletResponse response) {
		// Instantiate the list of phones to be added in the user entity
		List<Phone> phonesList = new ArrayList<Phone>();

		// Instantiate 'Phone' entity
		Phone phone = new Phone();
		phone.setNumber(number);
		phone.setDescription(desc);
		phonesList.add(phone);

		Address address = new Address();

		address.setCity(city);
		address.setState(state);
		address.setStreet(street);
		address.setZip(zip);
		phone.setAddress(address);
		// Get the id of the owner for this phone
		User temp = userService.getUser(Integer.parseInt(userId));
		if (temp == null) {
			// response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return "{\"Status\":\"Not Found\"}";
		}

		// Get the list of previous phones this owner has
		List<Phone> oldPhones = new ArrayList<Phone>();
		oldPhones = temp.getPhones();

		// Add the list of previous phones to the current phones list
		phonesList.addAll(oldPhones);
		temp.setPhones(phonesList);

		// Set the list of owners for this phone
		List<User> users = new ArrayList<User>();
		users.add(temp);
		phone.setUsers(users);
		try {
			phoneService.add(phone);
		} catch (Exception e) {
			if (e.getCause().getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				return "{\"Status\":\"Failure\"}";
			}
			return "{\"Status\":\"Exception\"}";
		}
		// In order to persist the data in the join table
		// Update the user entity with list of phones attached to it
		userService.modify(temp);
		// Add phone to the database
		Integer phoneId = phoneService.getPhoneId(phone);
		System.out.println("Phone ID = " + phoneId);
		response.setStatus(HttpServletResponse.SC_CREATED);
		return phoneId.toString();
		// return "{\"Id\":, \"Status\":\"Success\"}";
	}

	/**
	 * Returns the HTML view of phone details page.
	 * 
	 * @param id
	 *            Id of the phone to be displayed.
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/phone/{id}", method = RequestMethod.GET)
	public String getPhone(@PathVariable(value = "id") String id, Model model, HttpServletResponse response) {

		Phone phone = phoneService.getPhone(Integer.parseInt(id));
		model.addAttribute("id", id);
		List<User> assignedUsers = new ArrayList<User>();

		assignedUsers = phoneService.findAllUsers(Integer.parseInt(id));

		if (phone == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			model.addAttribute("id", id);
			model.addAttribute("name", "Phone");
			return "error";
		}
		Address address = new Address();
		address = phone.getAddress();
		model.addAttribute("city", address.getCity());
		model.addAttribute("state", address.getState());
		model.addAttribute("street", address.getStreet());
		model.addAttribute("zip", address.getZip());

		model.addAttribute("desc", phone.getDescription());
		model.addAttribute("number", phone.getNumber());
		model.addAttribute("listOfUsers", assignedUsers);

		return "showPhone";
	}

	/**
	 * Displays the phone entity in JSON format.
	 * 
	 * @param id
	 *            Id of the phone to be retrieved from the database
	 * @return Phone object in JSON format.
	 */
	@RequestMapping(value = "/phone/{id}", params = "json=true", method = RequestMethod.GET)
	public @ResponseBody Phone getPhone_JSON(@PathVariable(value = "id") String id) {

		Phone phone = phoneService.getPhone(Integer.parseInt(id));
		List<User> users = new ArrayList<User>();

		users = phoneService.findAllUsers(Integer.parseInt(id));
		phone.setUsers(users);
		return phone;
	}

	/**
	 * It is used to update the details of phone entity.
	 * 
	 * @param request
	 * @return View containing the phone entity details.
	 */
	@RequestMapping(value = "/phone/updatePhone", method = RequestMethod.POST)
	public String updatePhone(HttpServletRequest request, HttpServletResponse response) {

		Phone phone = new Phone();
		Integer id = Integer.parseInt(request.getParameter("id"));
		phone.setId(id);
		phone.setNumber(request.getParameter("phoneNumber"));
		phone.setDescription(request.getParameter("description"));
		Address address = new Address();

		address.setCity(request.getParameter("city"));
		address.setStreet(request.getParameter("street"));
		address.setZip(request.getParameter("zip"));
		address.setState(request.getParameter("state"));

		// Retrieve the list of all the owners of this phone
		List<User> users = new ArrayList<User>();
		users = phoneService.findAllUsers(id);
		phone.setUsers(users);
		phone.setAddress(address);
		phoneService.modify(phone);
		response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		return "redirect:/phone/" + id;
	}

	/**
	 * Deletes the phone with the given id.
	 * 
	 * @param phoneId
	 *            Id of the phone to be deleted
	 * @return
	 */
	@RequestMapping(value = "/phone/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String deletePhone(@PathVariable(value = "id") String phoneId) {

		Integer integer_phoneId = 0;
		try {
			integer_phoneId = Integer.parseInt(phoneId);
		} catch (NumberFormatException e) {
			System.out.println("NOT A VALID PHONE ID!!");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}

		if (phoneService.phoneExists(integer_phoneId)) {
			System.out.println("Phone exists with user id = " + phoneId);
			try {
				phoneService.delete(integer_phoneId);
			} catch (Exception e) {
				if (e.getCause().getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
					return "Failure";
				}
				System.out.println("Exception:  " + e.getMessage());
				return "Exception";
			}
			return "Success";
		} else {
			System.out.println("Phone does not exist for " + phoneId);
			return "Not found";
		}
	}

	/**
	 * Creates a new phone entity from URL encoded query parameters.
	 * 
	 * @param phoneId
	 *            Id of the phone entity to be added.
	 * @param desc
	 *            Description of the phone entity to be added.
	 * @param userId
	 *            Id of the user entity which owns this phone.
	 * @param phNo
	 *            Phone number of the phone entity.
	 * @param response
	 *            HTTP servlet response to set the response status of returned
	 *            entity.
	 * @param model
	 * @return
	 */

	@RequestMapping(value = "/phone/{id}", params = { "userId", "number", "description", "street", "city", "state",
			"zip" }, method = RequestMethod.POST)
	public String createPhone_URL_ENCODED(@PathVariable("id") String phoneId, @RequestParam("description") String desc,
			@RequestParam("userId") String userId, @RequestParam("number") String phNo,
			@RequestParam("street") String street, @RequestParam("zip") String zip, @RequestParam("city") String city,
			@RequestParam("state") String state, HttpServletResponse response, Model model) {

		Phone phone = new Phone();
		Phone dummyPhone = new Phone();
		// Check if the phone already exists
		dummyPhone = phoneService.getPhone(Integer.parseInt(phoneId));

		boolean phoneAlreadyAssociated = false;
		boolean phoneExists = false;
		// If the phone with given id exists then update the phone
		if (dummyPhone != null) {
			phoneExists = true;
			phone.setId(Integer.parseInt(phoneId));
		}
		// Phone does not exist. Create a new phone entity.
		phone.setNumber(phNo);
		phone.setDescription(desc);

		// Instantiate the list of phones to be added in the user entity
		List<Phone> phonesList = new ArrayList<Phone>();
		phonesList.add(phone);

		// Get the id of the owner for this phone
		User temp = userService.getUser(Integer.parseInt(userId));
		// Check if the user with given id exist or not
		if (temp == null) {
			model.addAttribute("id", userId);
			model.addAttribute("name", "User");
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return "error";
		}

		// Get the list of previous phones this owner has
		List<Phone> oldPhones = new ArrayList<Phone>();
		oldPhones = temp.getPhones();

		for (Phone p : oldPhones) {
			if (p.getId() == Integer.parseInt(phoneId)) {
				phoneAlreadyAssociated = true;
				break;
			}
		}
		// Add the list of previous phones to the current phones list
		phonesList.addAll(oldPhones);
		temp.setPhones(phonesList);

		// Set the list of owners for this phone
		List<User> users = new ArrayList<User>();
		users.add(temp);
		phone.setUsers(users);
		try {
			if (phoneExists)
				phoneService.modify(phone);
			else
				phoneService.add(phone);
		} catch (Exception e) {
			if (e.getCause().getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				// return "{\"Status\":\"Failure\"}";
				return "error";
			}
			return "error";
		}
		// In order to persist the data in the join table
		// Update the user entity with list of phones attached to it
		if (!phoneAlreadyAssociated)
			userService.modify(temp);
		// Add phone to the database
		Integer tempId = phoneService.getPhoneId(phone);
		response.setStatus(HttpServletResponse.SC_CREATED);
		return "redirect:/phone/" + tempId;
	}

	// http://localhost:8080/Cmpe275-Lab2/testing/phone/21?number=XX&description=YY&street=AAA&city=BBB&state=CCC&zip=95012&users[]=1&users[]=2
	@RequestMapping(value = "/testing/phone/{phoneId}", method = RequestMethod.POST)
	public String testing_URL_ENCODED(@PathVariable("phoneId") String phoneId, @RequestParam("description") String desc,
			@RequestParam("number") String number, @RequestParam("street") String street,
			@RequestParam("city") String city, @RequestParam("state") String state, @RequestParam("zip") String zip,
			@RequestParam("users[]") String[] usersArr) {
		System.out.println(street + phoneId + number + usersArr);
		System.out.println(city + state + zip);
		Address address = new Address();
		address.setCity(city);
		address.setState(state);
		address.setStreet(street);
		address.setZip(zip);
		System.out.println(address);
		return null;
	}

	// http://localhost:8080/Cmpe275-Lab2/testing/phone/21
	@RequestMapping(value = "/testing/phone/{id}", method = RequestMethod.GET)
	public @ResponseBody String testingPhone(@PathVariable(value = "id") String phoneId) {
		System.out.println(phoneId);
		return null;
	}

}


package edu.sjsu.cmpe275.lab2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import edu.sjsu.cmpe275.lab2.service.UserServiceImpl;

/**
 * @author Onkar Ganjewar Cmpe275-Lab2
 */

@Controller
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	@RequestMapping("/demo/{user_name}")
	public String helloWorld(final Map<String, Object> model, @PathVariable("user_name") String userName)
			throws Exception {
		String name = "Hello, " + userName;
		model.put("msg", name);
		return "helloWorld";
	}

	/**
	 * Displays the User information in JSON format.
	 *  
	 * @param id
	 * 			Id of the user to be fetched from database
	 * @param json
	 * 			If true, render the view
	 * @return
	 * 			User object in JSON format
	 */
	@RequestMapping(value="/user/{id}", params="json=true")
	public @ResponseBody User getUser_JSON(@PathVariable(value = "id") String id) {
		
		User user = userService.getUser(Integer.parseInt(id));
		List<Phone> phonesDir = new ArrayList<Phone>();
		phonesDir = userService.findAllPhones(Integer.parseInt(id));
		user.setListOfPhones(phonesDir);
		return user;
	}
	
	/**
	 * Display the home/index page of the application.
	 * 
	 * @param model
	 * @return returns the home page for registering a user.
	 */
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String renderHome(Model model) {
		model.addAttribute("user", new User());
		return "home";
	}

	/**
	 * Creates the user.
	 * 
	 * @param request
	 *            HTTP POST request for /addUser
	 * @return Returns the HTML view of the user details.
	 */
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String createUser(HttpServletRequest request) {
		Integer id = this.randomIdGenerator();
		User user = new User();
		user.setuserId(id);
		user.setFirstName(request.getParameter("firstName"));
		user.setLastName(request.getParameter("lastName"));
		user.setEmail(request.getParameter("email"));
		user.setTitle(request.getParameter("title"));

		// Instantiating a new value type object address for entity User
		Address address = new Address();
		address.setStreet(request.getParameter("address.street"));
		address.setCity(request.getParameter("address.city"));
		address.setState(request.getParameter("address.state"));
		address.setZip(request.getParameter("address.zip"));
		user.setAddress(address);

		userService.add(user);
		return "redirect:/user/" + id;
	}

	
	/**
	 * Updates the details of a particular user.
	 * 
	 * @param request
	 *            HTTP request containing the user details
	 * @return User details page in HTML format.
	 */
	@RequestMapping(value = "/user/updateUser", method = RequestMethod.POST)
	public String modifyUser(HttpServletRequest request) {

		User user = new User();
		Integer id = Integer.parseInt(request.getParameter("userId"));
		user.setuserId(id);
		user.setFirstName(request.getParameter("firstName"));
		user.setLastName(request.getParameter("lastName"));
		user.setEmail(request.getParameter("email"));
		user.setTitle(request.getParameter("title"));

		// Instantiating a new value type object address for entity User
		Address address = new Address();
		address.setStreet(request.getParameter("address.street"));
		address.setCity(request.getParameter("address.city"));
		address.setState(request.getParameter("address.state"));
		address.setZip(request.getParameter("address.zip"));
		user.setAddress(address);
		
		// Get the list of phones associated with the user
		List<Phone> phones = new ArrayList<Phone> ();
		phones = userService.findAllPhones(id);
		user.setListOfPhones(phones);

		userService.modify(user);
		return "redirect:/user/" + id;
	}

	/**
	 * Generates a unique ID for each user
	 * 
	 * @return the unique id
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
	 * Fetches the particular user of given id.
	 * 
	 * @param id
	 *            Id of the user to be fetched.
	 * @param json
	 *            optional; If true, returns the data rendered in JSON format.
	 * @param model
	 * @return Returns the User data in plain HTML/JSON format.
	 */
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public String getUser(@PathVariable(value = "id") String id,
			@RequestParam(value = "json", required = false) String json, Model model, HttpServletResponse response) {

		User user = userService.getUser(Integer.parseInt(id));
		model.addAttribute("id", id);

		if (user == null) {
			model.addAttribute("id", id);
			model.addAttribute("name", "User");
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return "error";
		}
		model.addAttribute("fname", user.getFirstName());
		model.addAttribute("lname", user.getLastName());
		model.addAttribute("email", user.getEmail());
		model.addAttribute("title", user.getTitle());

		// Populate the value object Address
		Address address = user.getAddress();

		model.addAttribute("city", address.getCity());
		model.addAttribute("state", address.getState());
		model.addAttribute("zip", address.getZip());
		model.addAttribute("street", address.getStreet());

		// Fetch the list of assigned phones for this user
		List<Phone> assignedPhones = new ArrayList<Phone> ();
		assignedPhones = userService.findAllPhones(Integer.parseInt(id));
		model.addAttribute("listOfPhones", assignedPhones);
		return "showUser";	
	}

	/**
	 * Delete the user with respective id.
	 * @param id
	 *            Id of the user to be deleted
	 * @param model
	 * @return Returns the home/index page of the application, if the user is
	 *         deleted successfully.
	 */

	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public @ResponseBody String deleteUser(@PathVariable(value = "id") String userId) {

		Integer integer_userId = 0;
		try {
			integer_userId = Integer.parseInt(userId);
		} catch (NumberFormatException e) {
			System.out.println("NOT A VALID USER ID!!");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}

		if (userService.userExists(integer_userId)) {
			System.out.println("User exists with user id = " + userId);
			userService.delete(integer_userId);
			return "Success";
		} else {
			System.out.println("User does not exist for " + userId);
			return "Not found";
		}
	}

	/**
	 * Creates the user entity from URL-Encoded parameters. 
	 * TODO: Not yet tested
	 * 
	 * @param id
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @param title
	 * @param city
	 * @param state
	 * @param zip
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/user/{id}", params = { "firstName", "lastName", "email", "city", "state", "zip",
			"title" }, method = RequestMethod.GET)
	public String createUser_URL_ENCODED(@PathVariable("id") String id, @RequestParam("firstName") String firstname,
			@RequestParam("lastName") String lastname, @RequestParam("email") String email,
			@RequestParam("title") String title, @RequestParam("city") String city, @RequestParam("state") String state,
			@RequestParam("zip") String zip, Map<String, Object> map) {
		User user = new User();
		user.setuserId(Integer.parseInt(id));
		user.setFirstName(firstname);
		user.setLastName(lastname);
		user.setEmail(email);
		user.setTitle(title);

		// Populate the value object Address of Entity "User"
		Address address = new Address();
		address.setCity(city);
		address.setState(state);
		address.setZip(zip);

		user.setAddress(address);

		User temp = userService.getUser(user.getuserId());
		if (temp == null) {
			userService.add(user);
		} else {
			userService.modify(user);
		}
		map.put("user", user);
		return "showUser";
	}
}

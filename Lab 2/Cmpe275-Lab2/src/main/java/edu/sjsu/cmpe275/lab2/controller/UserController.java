
package edu.sjsu.cmpe275.lab2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

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

	// Dummy method to test json rendering.
	@RequestMapping(value = "/json/test", method = RequestMethod.GET)
	public @ResponseBody User getUserInJSON_dummy() {
		User user = new User();
		user.setuserId(555550);
		user.setFirstName("firstName");
		user.setLastName("lastName");
		user.setEmail("email");
		user.setTitle("title");

		// Instantiating a new value type object address for entity User
		Address address = new Address();
		address.setStreet("address.street");
		address.setCity("address.city");
		address.setState("address.state");
		address.setZip("address.zip");
		user.setAddress(address);

		Phone phone = new Phone();
		phone.setDescription("desc");
		phone.setPhoneNumber("123123");
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		user.setListOfPhones(phones);
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

		// // Instantiate 'Phone' entity
		// Phone phone = new Phone();
		// phone.setDescription("desc");
		// phone.setPhoneNumber("123123");
		// //
		// phone.setPhoneNumber(request.getParameter("listOfPhones.phoneNumber"));
		// //
		// phone.setDescription(request.getParameter("listOfPhones.description"));
		// List<Phone> phones = new ArrayList<Phone>();
		// phones.add(phone);
		// user.setListOfPhones(phones);

		userService.add(user);
		return "redirect:/user/" + id;
	}

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

		userService.modify(user);
		return "redirect:/user/" + id;
	}

	// @RequestMapping(value = { "/updateUser" }, method = RequestMethod.POST)
	// public String changeUser(@ModelAttribute User user, BindingResult result,
	// @RequestParam String action,
	// Map<String, Object> map) {
	// // Profile p=profileService.getProfile(profile.getId());
	// userService.modify(user);
	// map.put("user", user);
	// return "showUser";
	// }

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
			@RequestParam(value = "json", required = false) String json, Model model) {

		User user = userService.getUser(Integer.parseInt(id));
		model.addAttribute("id", id);

		if (user == null) {
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
		if (json == null)
			return "showUser";
		else
			return "showUserJSON";
	}

	// @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	// public String getUser(@PathVariable("id") String id, Model model) {
	// // ModelAndView modelProfile = new ModelAndView("profile");
	// User user = new User();
	// user = userService.getUser(Integer.parseInt(id));
	// if (user != null) {
	// model.addAttribute("user", user);
	// // modelProfile.addObject(p);
	// model.addAttribute("listOfUsers", userService.getAllUsers());
	// return "viewUser";
	// } else {
	// model.addAttribute("id", id);
	// return "notFound";
	// }
	// }

	// /**
	// * Sample handlers for DELETE request from browser
	// *
	// * @param id
	// * @param model
	// * @return
	// */
	// @RequestMapping(value = "/sample/{id}", method = RequestMethod.GET)
	// public String getSample(@PathVariable(value = "id") String id, Model
	// model) {
	// return "home";
	// }
	//
	// /**
	// * @see https://github.com/JVerstry/Web-Related-Examples
	// * @param id
	// * @param model
	// * @return
	// */
	// @RequestMapping(value = "/sample/{id}", method = RequestMethod.DELETE)
	// public String deleteSample(@PathVariable(value = "id") String id, Model
	// model) {
	// System.out.println("User does not exist for " + id);
	// model.addAttribute("id", id);
	//
	// return "error";
	// }

	/**
	 * Updates the details of a particular user.
	 * 
	 * @param request
	 *            HTTP request containing the user details
	 * @return User details page in HTML format.
	 */
	// @RequestMapping(value = "/user/{id}", method = RequestMethod.POST)
	// public String updateUser(HttpServletRequest request, @PathVariable(value
	// = "id") String userId, Model model) {
	// System.out.println("UPDATE A PROFILE");
	//
	// Integer integerId = 0;
	// try {
	// integerId = Integer.parseInt(userId);
	// } catch (NumberFormatException e) {
	// System.out.println("NOT A VALID USER ID!!");
	// e.printStackTrace();
	// } catch (Exception e) {
	// System.out.println("Exception: " + e.getMessage());
	// }
	//
	// User user = new User();
	// user.setuserId(integerId);
	// user.setFirstName(request.getParameter("firstName"));
	// user.setLastName(request.getParameter("lastName"));
	// user.setEmail(request.getParameter("email"));
	// user.setTitle(request.getParameter("title"));
	//
	// // Instantiating a new value type object address for entity User
	// Address address = new Address();
	// address.setStreet(request.getParameter("address.street"));
	// address.setCity(request.getParameter("address.city"));
	// address.setState(request.getParameter("address.state"));
	// address.setZip(request.getParameter("address.zip"));
	// user.setAddress(address);
	//
	// userService.modify(user);
	// return "forward:/user/";
	//// return "redirect:/user/" + id;
	// }

	/**
	 * Delete the user with respective id.
	 * 
	 * @param id
	 *            Id of the user to be deleted
	 * @param model
	 * @return Returns the home/index page of the application, if the user is
	 *         deleted successfully.
	 */

	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public String deleteUser(@PathVariable(value = "id") String userId, Model model) {
		// TODO: Not able to render the index/view after the entry is deleted
		// from the database.

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
			// Not getting rendered -- HTTP 405: Method not supported
			return "home";
			// return "redirect:/user/";
		} else {
			System.out.println("User does not exist for " + userId);
			model.addAttribute("id", userId);
			return "error";
		}
	}

	/**
	 * Creates the user entity from URL-Encoded parameters. TODO: Not yet
	 * tested.
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
		// map.put("listOfProfiles", userService.getAllUsers());
		return "showUser";
	}
}

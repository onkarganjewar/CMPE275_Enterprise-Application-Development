package edu.sjsu.cmpe275.lab2.controller;

import java.util.ArrayList;
import java.util.List;

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

	@RequestMapping(value = "/phone", method = RequestMethod.GET)
	public String renderHome(Model model) {
		model.addAttribute("phone", new Phone());
		return "phoneIndex";
	}

	/**
	 * Creates the new phone entity in the database.
	 * 
	 * @param request
	 * 			Servlet request containing all the attributes of the phone entity
	 * @return
	 * 			HTML view holding all the details of newly added phone  
	 * @throws Exception
	 */
	@RequestMapping(value = "/addPhone", method = RequestMethod.POST)
	public String createPhone(HttpServletRequest request) throws Exception {
		// Instantiate the list of phones to be added in the user entity
		List<Phone> phonesList = new ArrayList<Phone>();

		// Instantiate 'Phone' entity
		Phone phone = new Phone();
		phone.setPhoneNumber(request.getParameter("phoneNumber"));
		phone.setDescription(request.getParameter("description"));
		phonesList.add(phone);

		// Get the id of the owner for this phone
		String obj = request.getParameter("listOfUsers");
		User temp = userService.getUser(Integer.parseInt(obj));
		// Get the list of previous phones this owner has
		List<Phone> oldPhones = new ArrayList<Phone>();
		oldPhones = temp.getListOfPhones();

		// Add the list of previous phones to the current phones list
		phonesList.addAll(oldPhones); 
		temp.setListOfPhones(phonesList);

		// Set the list of owners for this phone
		List<User> users = new ArrayList<User>();
		users.add(temp);
		phone.setListOfUsers(users);
		phoneService.add(phone);
		
		// In order to persist the data in the join table
		// Update the user entity with list of phones attached to it
		userService.modify(temp);
		// Add phone to the database
		Integer i = phoneService.getPhoneId(phone);
		
		// Redirect to the phone details HTML page
		return "redirect:/phone/" + i;
	}

	/**
	 * Returns the HTML view of phone details page.
	 * 
	 * @param id
	 * @param json
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/phone/{id}", method = RequestMethod.GET)
	public String getPhone(@PathVariable(value = "id") String id,
			@RequestParam(value = "json", required = false) String json, Model model, HttpServletResponse response) {

		Phone phone = phoneService.getPhone(Integer.parseInt(id));
		model.addAttribute("id", id);
		List<User> assignedUsers = new ArrayList<User> ();
		
		assignedUsers = phoneService.findAllUsers(Integer.parseInt(id));
		
		if (phone == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			model.addAttribute("id", id);
			model.addAttribute("name", "Phone");
			return "error";
		}
		model.addAttribute("desc", phone.getDescription());
		model.addAttribute("number", phone.getPhoneNumber());
		model.addAttribute("listOfUsers", assignedUsers);
		return "showPhone";
	}
	
	/**
	 * Displays the phone entity in JSON format.
	 * 	 <p>TODO: Fix JSON mapper bi-directional relationship problem (Jackson Infinity Recursion Problem)</p>
	 * @see 
	 * 		http://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion</u>
	 * 		http://stackoverflow.com/questions/22615317/serialize-bi-directional-jpa-entities-to-json-with-jackson
	 * @param id
	 *      Id of the phone to be retrieved from the database
	 * @return
	 * 		Phone object in JSON format.
	 */
	@RequestMapping(value = "/phone/{id}", params = "json=true", method = RequestMethod.GET)
	public @ResponseBody Phone getPhone_JSON(@PathVariable(value = "id") String id) {

		Phone phone = phoneService.getPhone(Integer.parseInt(id));

		// Populate user entity with dummy data
		List<User> users = new ArrayList<User>();
		User user = new User();
		user.setuserId(555550);
		user.setFirstName("firstName");
		user.setLastName("lastName");
		user.setEmail("email");
		user.setTitle("title");
		users.add(user);
		
		List<User> tempArr = new ArrayList<User>();
		
		// Circular reference problem: Phone --> listOfUsers[] --> listOfPhones[] --> Phone
//		users = phoneService.findAllUsers(Integer.parseInt(id));
		tempArr = phoneService.findAllUsers(Integer.parseInt(id));
		
		phone.setListOfUsers(users);
		return phone;
	}

	/**
	 * It is used to update the details of phone entity. 
	 * 
	 * @param request
	 * @return 
	 * 			View containing the phone entity details.
	 */
	@RequestMapping(value = "/phone/updatePhone", method = RequestMethod.POST)
	public String updatePhone(HttpServletRequest request) {

		Phone phone = new Phone();
		Integer id = Integer.parseInt(request.getParameter("id"));
		phone.setPhoneId(id);
		phone.setPhoneNumber(request.getParameter("phoneNumber"));
		phone.setDescription(request.getParameter("description"));

		// Retrieve the list of all the owners of this phone
		List<User> users = new ArrayList<User>();
		users = phoneService.findAllUsers(id);
		phone.setListOfUsers(users);
		
		phoneService.modify(phone);
		return "redirect:/phone/" + id;
	}
	
	@RequestMapping(value = "/phone/{id}", method = RequestMethod.DELETE)
	public String deleteUser(@PathVariable(value = "id") String userId, Model model, HttpServletResponse response) {

		Integer integer_userId = 0;
		try {
			integer_userId = Integer.parseInt(userId);
		} catch (NumberFormatException e) {
			System.out.println("NOT A VALID PHONE ID!!");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}

		if (phoneService.phoneExists(integer_userId)) {
			System.out.println("Phone exists with user id = " + userId);
			phoneService.delete(integer_userId);
			// Not getting rendered -- HTTP 405: Method not supported
			return "home";
			// return "redirect:/user/";
		} else {
			System.out.println("Phone does not exist for " + userId);
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			model.addAttribute("id", userId);
			model.addAttribute("name", "Phone");
			return "error";
		}
	}
}

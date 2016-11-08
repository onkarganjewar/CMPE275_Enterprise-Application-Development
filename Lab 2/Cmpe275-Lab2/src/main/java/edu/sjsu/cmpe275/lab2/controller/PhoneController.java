/**
 * 
 */
package edu.sjsu.cmpe275.lab2.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.sjsu.cmpe275.lab2.model.Phone;
import edu.sjsu.cmpe275.lab2.model.User;
import edu.sjsu.cmpe275.lab2.service.PhoneService;

/**
 * @author Onkar Ganjewar
 * Cmpe275-Lab2
 */
@Controller
public class PhoneController {

	@Autowired
	private PhoneService phoneService;

	@RequestMapping(value = "/phone", method = RequestMethod.GET)
	public String renderHome(Model model) {
		model.addAttribute("phone", new Phone());
		return "phoneIndex";
	}
	
	@RequestMapping(value = "/addPhone", method = RequestMethod.POST)
	public String createPhone(HttpServletRequest request) throws Exception {
		Phone phone = new Phone();

		// Instantiate 'Phone' entity
		phone.setPhoneNumber(request.getParameter("phoneNumber"));
		phone.setDescription(request.getParameter("description"));

		User user = new User();
		user.setuserId(28);
		// user.setFirstName(request.getParameter("listOfUsers.firstName"));

		List<User> users = new ArrayList<User>();
		users.add(user);
		phone.setListOfUsers(users);

		// Instantiating a new value type object address for entity User
		// Address address = new Address();
		// address.setStreet(request.getParameter("address.street"));
		// address.setCity(request.getParameter("address.city"));
		// address.setState(request.getParameter("address.state"));
		// address.setZip(request.getParameter("address.zip"));
		// phone.setAddress(address);

		phoneService.add(phone);
		// Integer tid = phoneService.getKey(phone);
		return "redirect:/phone/";
	}

	@RequestMapping(value = "/phone/{id}", method = RequestMethod.GET)
	public String getPhone(@PathVariable(value = "id") String id,
			@RequestParam(value = "json", required = false) String json, Model model) {

		Phone phone = phoneService.getPhone(Integer.parseInt(id));
		model.addAttribute("id", id);

		if (phone == null) {
			return "error";
		}

		model.addAttribute("desc", phone.getDescription());
		model.addAttribute("number", phone.getPhoneNumber());

		// List<User> users = phone.getListOfUsers();
		// model.addAttribute("section_name", users.get(0).getuserId());

		// Populate the value object Address
		// Address address = user.getAddress();
		//
		// model.addAttribute("city", address.getCity());
		// model.addAttribute("state", address.getState());
		// model.addAttribute("zip", address.getZip());
		// model.addAttribute("street", address.getStreet());
		if (json == null)
			return "showPhone";
		else
		{
//			getShopInJSON();
			return "null";
		}
	}

	/**
	 * It is used to update the details of phone entity.
	 * TODO: Not yet tested.
	 * @param request
	 * @return the view
	 */
	@RequestMapping(value = "/phone/updatePhone", method = RequestMethod.POST)
	public String updateProfile(HttpServletRequest request) {
		System.out.println("UPDATING A PHONEE NOW");
		Phone phone = new Phone();
		Integer id = Integer.parseInt(request.getParameter("id"));
		phone.setPhoneId(id);
		phone.setPhoneNumber(request.getParameter("phoneNumber"));
		phone.setDescription(request.getParameter("description"));
		phoneService.modify(phone);
		return "redirect:/phone/" + id;
	}

}

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
import edu.sjsu.cmpe275.lab2.service.UserServiceImpl;

/**
 * @author Onkar Ganjewar
 * Cmpe275-Lab2
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
	
	@RequestMapping(value = "/addPhone", method = RequestMethod.POST)
	public String createPhone(HttpServletRequest request) throws Exception {
		Phone phone = new Phone();

		// Instantiate 'Phone' entity
		phone.setPhoneNumber(request.getParameter("phoneNumber"));
		phone.setDescription(request.getParameter("description"));
		User u1 = userService.getAllUsers().get(0);
		List<User> users = new ArrayList<User>();
		users.add(u1);
		phone.setListOfUsers(users);
		phoneService.add(phone);
		
		Integer i = phoneService.getPhoneId(phone);
		System.out.println("FOUND USER WITH ID"+i);
		return "redirect:/phone/"+i;
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
		if (json == null)
			return "showPhone";
		else
		{
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

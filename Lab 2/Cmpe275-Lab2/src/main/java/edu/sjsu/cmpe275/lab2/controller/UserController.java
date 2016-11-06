
package edu.sjsu.cmpe275.lab2.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Onkar Ganjewar Cmpe275-Lab2
 */

@Controller
public class UserController {

//	@RequestMapping("/jsptest")
//	public String test(ModelAndView modelAndView) {
//		return "index";
//	}

	@RequestMapping("/demo/{user_name}")
	public String helloWorld(final Map<String, Object> model, @PathVariable("user_name") String userName)
			throws Exception {
		String name = "Hello, " + userName;
		model.put("msg", name);
		return "helloWorld";
	}
}

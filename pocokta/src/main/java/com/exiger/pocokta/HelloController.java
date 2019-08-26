package com.exiger.pocokta;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView adminPage() {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Admin");
		model.addObject("message", "This is protected page - Admin Page!");
		model.setViewName("admin");
		return model;
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ModelAndView userPage() {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "user");
		model.addObject("message", "This is protected page - User Page!");
		model.setViewName("user");
		return model;
	}


}

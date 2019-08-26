package com.alejoqm.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class OktaController {

	@RequestMapping(path = "/oktaCallback", method = RequestMethod.GET)
	public void okta(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String,String> allParams) throws IOException {

		if(allParams.get("code") != null) {
			OktaServiceClient oktaServiceClient = new OktaServiceClient();
			response.addCookie(new Cookie("token", URLEncoder.encode( oktaServiceClient.getToken(allParams.get("code")), "UTF-8" )));
			//response.sendRedirect("https://localhost.outsideiq.com:8443/dashboard/#/home");
		} else {
					}
	}
}

package com.exiger.services.accounting.controllers;

import com.exiger.services.accounting.services.AdjudicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Endpoint to test that the app is running")
@RequestMapping
@Controller
@RefreshScope
public class ApiController {

	private static final Logger log = LoggerFactory.getLogger(ApiController.class);

	@Autowired AdjudicationService pingService;

	@ApiOperation(value = "endpoint to verify if the app is running", response = String.class)
	@RequestMapping(path = "/ping", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody ResponseEntity<Object> pingService() {
		log.info("Starting the ping endpoint");

		// uncomment this line to call external service pingService
		// return ResponseEntity.ok(pingService.ping());
		return ResponseEntity.ok("Application is running successfully " + pingService.ping());
	}
}

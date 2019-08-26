package com.exiger.services.accounting.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "adjudication-service")
public interface AdjudicationService {

	@RequestMapping(method = RequestMethod.GET, value = "/ping")
	public String ping();
}

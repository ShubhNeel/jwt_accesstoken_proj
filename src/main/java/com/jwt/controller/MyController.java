package com.jwt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
	
	Logger logger = LoggerFactory.getLogger(MyController.class);
	
	
	@RequestMapping(path = "/test")
	public void test() {
		
		logger.info("Inside MyController");
	
	}

}

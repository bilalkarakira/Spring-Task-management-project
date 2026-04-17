package com.bilal.taskmanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloTestController {
	private static final Logger logger = LoggerFactory.getLogger(HelloTestController.class);
	@GetMapping("/hello")
	public String ping() {
	    System.out.println("PING RECEIVED!");
	    logger.info("Ping pong");
	    return "Pong";
	}
}

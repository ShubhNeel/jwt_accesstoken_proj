package com.jwt.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jwt.constants.JWTConstants;
import com.jwt.entity.UserData;
import com.jwt.service.UserDetailsServiceImpl;
import com.jwt.util.JWTUtils;



public class JWTController {

	
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	JWTUtils jwtUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@RequestMapping(value = JWTConstants.GET_AUTH_TOKEN, method = RequestMethod.POST)
	public ResponseEntity<String> creatAuthenticationToken(@RequestBody UserData user) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		//UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
		UserDetails userDetails = new User(user.getUsername(), user.getPassword(), new ArrayList<>());

		String jwt = jwtUtils.generateToken(userDetails);

		return ResponseEntity.ok(jwt);
}
}

package com.example.demo.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.UserException;
import com.example.demo.model.User;

import com.example.demo.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class UserController

{

	@Autowired

	private UserService userService;

	@GetMapping("/getAllUsers")

	public ResponseEntity<?> getAllUsers() throws UserException

	{

		List<User> userList = userService.getAllUsers();
		if (userList != null)

		{

			return new ResponseEntity<List<User>>(userList, HttpStatus.OK);

		}

		return new ResponseEntity<String>("userList is empty", HttpStatus.OK);

	}

} 
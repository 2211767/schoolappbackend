
package com.example.demo.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.UserException;
import com.example.demo.model.User;
import com.example.demo.serviceImpl.UserServiceImpl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user/v1")
public class AuthenticationController {
	private Map<String, String> mObj = new HashMap<String, String>();

	@Autowired
	private UserServiceImpl userServiceImpl;

	@PostMapping("/registerUser")
	public ResponseEntity<?> addUser(@RequestBody User user) throws UserException {

		return new ResponseEntity<User>(userServiceImpl.addUser(user), HttpStatus.CREATED);
	}

	@PostMapping("/loginUser")
	public ResponseEntity<?> loginUser(@RequestBody User user) {
		try {
			String jwtToken = generateToken(user.getUserName(), user.getPassword());
			User User1=userServiceImpl.findByUserName(user.getUserName());
			mObj.put("message", "User successfully logged in");
//			mObj.put("userName", User1.getUserName());
//			mObj.put("userType", User1.getUserRole());
//			mObj.put("Password", User1.getPassword());
//			mObj.put("userId", User1.getUserId().toString());
			mObj.put("token", jwtToken);
		} catch (Exception e) {
			mObj.put("message", "User unsuccessful to be logged in");
			mObj.put("token", null);
		}
		return new ResponseEntity<>(mObj, HttpStatus.CREATED);
	}

	@ExceptionHandler(UserException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<String> handleSchoolStafExceptions(UserException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
	}

	public String generateToken(String userName, String password) throws ServletException, Exception {
		String jwtToken = "";
		if (userName == null || password == null) {
			throw new ServletException("Please send valid username and password");
		}
		boolean flag = userServiceImpl.validateUserLogin(userName, password);
		if (!flag) {
			throw new ServletException("Invalid credentials");
		} else {
			jwtToken = Jwts.builder().setSubject(userName).setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + 360000099))
					.signWith(SignatureAlgorithm.HS256, "my secret sign").compact();

		}

		return jwtToken;
	}
}
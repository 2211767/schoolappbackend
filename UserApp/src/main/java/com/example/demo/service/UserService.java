package com.example.demo.service;

import java.util.List;

import com.example.demo.exceptions.UserException;
import com.example.demo.model.User;

public interface UserService {

	List<User> getAllUsers();

	boolean validateUserLogin(String userName, String password);

	User addUser(User user) throws UserException;

}

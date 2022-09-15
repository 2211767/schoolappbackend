package com.example.demo.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.UserException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public User addUser(User user) throws UserException {
		User user1 = userRepo.findByUserName(user.getUserName());
		if (user1 == null) {
			if (user != null) {
				if (user.getUserName().contains("@") && user.getUserName().contains(".")) {
					return userRepo.saveAndFlush(user);
				} else {
					throw new UserException("User Name should be @ and . ");
				}
			} else {
				return null;
			}
		} else {
			throw new UserException("User Already Present");
		}

	}

	@Override
	public List<User> getAllUsers() {
		List<User> userList = userRepo.findAll();

		if (userList != null & userList.size() > 0) {
			return userList;
		} else
			return null;
	}

	@Override
	public boolean validateUserLogin(String userName, String password) {
		User user1 = userRepo.validateUserAndPassword(userName, password);
		if (user1 != null) {
			return true;

		} else {
			return false;

		}
	}

	public User findByUserName(String userName) {
		User user1 = userRepo.findByUserName(userName);
		if (user1 != null) {
			return user1;

		} else {
			return null;

		}
	}

}

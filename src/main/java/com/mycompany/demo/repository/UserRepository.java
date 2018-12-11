package com.mycompany.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.demo.entity.UserModel;

@Repository
public interface UserRepository<User> extends CrudRepository<UserModel, String>{

	/*void registerUser(UserModel user) throws UserAlreadyExistsException;

	List<UserModel> listAllUsers();

	UserModel getUserById(UUID id);

	void deleteUser(UUID id);*/
}

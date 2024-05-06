package com.example.springboot.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;


import java.util.List;

public interface UserRepository extends MongoRepository<User, Integer> {

    //CRUD - Create & Update
    User save(User user);


    //CRUD - Read
    List<User> findAll();
    Optional<User> findTopByOrderByIdDesc();
    @Query("{'id': ?0}")
    Optional<User> getUserById(int userId);
    @Query("{'$or': [{'name': ?0}, {'mail': ?0}]}")
    Optional<User> getUserByNameOrMail(String nameOrMail);
    @Query("{'mail': ?0}")
    Optional<User> getUserByMail(String mail);
    @Query("{'id': {'$in': ?0}}")
    List<User> getUsersFromList(@Param("ids") List<Integer> ids);


    // CRUD - Delete
    void deleteById(int id);


    // Account Management
    @Query("{'$or': [{'name': ?0}, {'mail': ?0}]}")
    Optional<User> login(String nameOrMail, String password);
}
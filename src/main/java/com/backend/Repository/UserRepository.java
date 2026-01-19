package com.backend.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.backend.Model.User;

public interface UserRepository extends MongoRepository<User, String> {

    boolean existsByEmail(String email);

}

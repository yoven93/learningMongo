package com.accenture.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.accenture.domain.User;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

}

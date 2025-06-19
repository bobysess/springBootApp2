package com.bobysess.springBootApp2.controller;

import java.util.List;

import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.micrometer.observation.annotation.Observed;

//@EnableJdbcRepositories
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    public List<User> findAll(); 
}

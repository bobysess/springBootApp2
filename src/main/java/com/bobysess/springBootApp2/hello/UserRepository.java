package com.bobysess.springBootApp2.hello;

import java.util.concurrent.CompletableFuture;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
        
    CompletableFuture<User> findOneById(Long id);
}

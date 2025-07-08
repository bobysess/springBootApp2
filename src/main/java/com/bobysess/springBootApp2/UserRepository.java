package com.bobysess.springBootApp2;

import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
@JaversSpringDataAuditable 
public interface UserRepository extends JpaRepository<User, Long> {}
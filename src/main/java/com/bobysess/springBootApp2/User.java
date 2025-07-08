package com.bobysess.springBootApp2;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor 
public class User {
    @Id 
    Long id;
    private String name;
    private String email;
    private String password;
}
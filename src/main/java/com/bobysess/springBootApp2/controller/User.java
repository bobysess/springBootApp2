package com.bobysess.springBootApp2.controller;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Table("USERS")
public class User {
        @Id
        private Long id; 
        private String email;
        private String username; 
        private String password;

        public User(String username, String password, String email) {
            this.username = username;
            this.password = password;
            this.email = email; 
        } 
}

package com.bobysess.springBootApp2.user;

import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.hateoas.Affordance;
import org.springframework.hateoas.AffordanceModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.SlicedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.hateoas.SlicedModel.SliceMetadata;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UserController {
    private final Map<UUID, User> users = new HashMap<>();
    @GetMapping("/user/{id}")
    public EntityModel<User> get(@PathVariable UUID id) {
        return EntityModel.of(users.get(id),
            linkTo(methodOn(UserController.class).get(id)).withSelfRel(),
            linkTo(methodOn(UserController.class).getAll()).withRel("users"),
            linkTo(methodOn(UserController.class).delete(id)).withRel("delete"),
            linkTo(methodOn(UserController.class).create(null)).withRel("create"),
            linkTo(methodOn(UserController.class).update(id, null)).withRel("update")
        );
    }

    @GetMapping("/user")
    public SlicedModel<User> getAll() {
        users.values().forEach(user -> user.add(Link.of("/user/{id}").expand(user.id)));
        return SlicedModel.of(users.values(), new SliceMetadata(10l, 1l));
        //return PagedModel.of(users.values(), new PageMetadata(10l, 1l, 50l));
        //return CollectionModel.of(users.values()).add(Link.of("/user").withSelfRel());
        // return users.values();
    }

    @DeleteMapping("/user/{id}")
    public User  delete(@PathVariable UUID id) {
        if (users.containsKey(id)) {
            users.remove(id);
        }

        return null; 
    }

    @PutMapping("/user/{id}")
    public User update(@PathVariable UUID id, @RequestBody User user) {
        return users.replace(id, user);
    }

    @PostMapping("/user")
    public User create(@RequestBody User user) {
        user.setId(UUID.randomUUID());
        users.put(user.getId(), user);
        return user;
    }

    @PostConstruct
    void createSomeUsers () {
        User user1 = new User(UUID.fromString("d2bc0bad-68a7-422f-ae87-8846cedb04e7"), "John", "Doe", 25);
        users.put(user1.getId(), user1);

        User user2 = new User(UUID.randomUUID(), "Jane", "Smith", 30);
        users.put(user2.getId(), user2);

        User user3 = new User(UUID.randomUUID(), "Bob", "Johnson", 35);
        users.put(user3.getId(), user3);

        User user4 = new User(UUID.randomUUID(), "Alice", "Brown", 28);
        users.put(user4.getId(), user4);
    }

    @AllArgsConstructor
    @Data
    public static class User extends RepresentationModel<User>{
        private UUID id;
        private String firstname;
        private String lastname;
        private int old;
    }
}

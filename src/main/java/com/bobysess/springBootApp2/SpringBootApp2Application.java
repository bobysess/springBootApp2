package com.bobysess.springBootApp2;

import jakarta.persistence.ElementCollection;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.metamodel.annotation.Entity;
import org.javers.core.metamodel.annotation.Id;
import org.javers.core.metamodel.annotation.TypeName;
import org.javers.repository.jql.JqlQuery;
import org.javers.repository.jql.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import org.springframework.context.annotation.EnableMBeanExport;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringBootApp2Application {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {
		SpringApplication.run(SpringBootApp2Application.class, args);
	}

	

	@Bean
	ApplicationRunner javersTest() {
		return args -> {
			var javers = JaversBuilder.javers().build();

			// var person2 = new Person(2l, "arsel2", 2, GENDER.FEMALE);
			// var person1 = new Person(1l, "arsel1", 1, GENDER.MALE);
			// Diff diff = javers.compare(person1, person2); //javers.findShadows();
			var post = new Post();
			post.setId(1L);
			post.setTitel("This is a test post");
			post.setSummary("This is a test post");
			post.setText("This is a test post");

			var commit = javers.commit("arsel", post);

			post.setAuthor(new Author(1, "Müller"));
			commit = javers.commit("arsel", post);

			post.getTags().add("tag1");
			post.getTags().add("tag2");
			commit = javers.commit("arsel", post);

			post.getTags().remove("tag1");
			commit = javers.commit("arsel", post);

			post.getAuthor().setName("Arsel 3");
			commit = javers.commit("arsel", post);

			post.setAuthor(null);
			commit = javers.commit("arsel", post);

			post.getComments().add(new Comment(1l, "comment 1", "comment asdfdf asd", "kevin"));
			commit = javers.commit("arsel", post);

			post.getComments().getFirst().setText("comment 2");
			commit = javers.commit("arsel", post);

			var comment = post.getComments().removeFirst();
			commit = javers.commit("arsel", post);

			commit = javers.commitShallowDelete("arsel", comment);

			var changes = javers.findChanges(QueryBuilder.byInstanceId(1, Post.class).build());

			// logger.info("changes {}", changes);
		};
	}

	@Entity
	@Data
	class Post {
		@Id
		private long id;
		private String titel;
		private String summary;
		private String text;
		private PostStatus status = PostStatus.PENDING;
		private final List<String> tags = new ArrayList<>();
		private Author author;
		private final List<Comment> comments = new ArrayList<>();
		private Instant createdAt;
		private Instant publishedAt;
	}

	enum PostStatus {
		PENDING,
		REVIEW,
		PUBLISHED
	}

	@TypeName("comment")
	@Entity
	@Data
	@AllArgsConstructor
	class Comment {
		@Id
		private long id;
		private String titel;
		private String text;
		private String author;
	}

	@Entity
	@Data
	@AllArgsConstructor
	class Author {
		@Id
		private long id;
		private String name;
	}

	enum GENDER {
		MALE, FEMALE;
	}

	@Entity
	@Data
	@AllArgsConstructor
	class Person {
		@Id
		private long id;
		private String name;
		private int old;
		private GENDER gender;
	}
}

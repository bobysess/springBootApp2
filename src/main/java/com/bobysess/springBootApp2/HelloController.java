package com.bobysess.springBootApp2;

import java.net.URI;
import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.client.HttpSyncGraphQlClient;
import org.springframework.graphql.client.WebSocketGraphQlClient;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@EnableScheduling
@Controller
// @RequiredArgsConstructor
public class HelloController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Map<Long, Author> authors = new HashMap<>(); 
    private final Map<Long, Book> books  = new HashMap<>(); 
    private final HttpSyncGraphQlClient syncClient; 
    private final WebSocketGraphQlClient wsClient; 

    public HelloController (RestClient.Builder restBuilder) {
        syncClient = HttpSyncGraphQlClient.create(restBuilder.baseUrl("http://localhost:8080/graphql").build());
        wsClient = WebSocketGraphQlClient.builder(
            URI.create("ws://localhost:8080/graphql"),
            new ReactorNettyWebSocketClient()
        ).build();
    }
    @QueryMapping
    public String hello(@Argument String name) {  
        return "hello  " + name; 
    }

    @QueryMapping
    public Book book(@Argument long id) {
        return books.get(id);
    }

    // @QueryMapping
    // public BookWithAuthor book2(@Argument long id) {
    //     var book = books.get(id);
    //     return new BookWithAuthor(book.id, book.title, book.summary, authors.get(book.authorId));
    // }

    @QueryMapping
    public Author author(@Argument long id) {
        if (!authors.containsKey(id)) {
            throw new NotFoundAuthorException("couldn't found author with id: " + id);        
        }
        
        return authors.get(id);        
    }


    @QueryMapping
    public Collection<Book> books() {
        return books.values();
    }

    @QueryMapping
    public Collection<Author> authors () {
        return authors.values(); 
    }
    
    @MutationMapping
    public Book addBook (@Argument Book book) {
        return books.putIfAbsent(book.id, book);     
    }

    @MutationMapping
    public Book deleteBook (@Argument long id) {
        return books.remove(id);     
    }

    @SchemaMapping
    public Author author (Book book) {
        return authors.get(book.authorId);
    }

    // @SchemaMapping
    // public List<Book> books(Author author) {
    //     return books.values().stream().filter(b -> b.authorId == author.id ).toList(); 
    // }


    @BatchMapping
    public Map<Author, List<Book>> books (List<Author> authors) {
        logger.info("=====  Batching booking =====");
        return authors
            .stream()
            .collect(
                    HashMap::new, 
                    (map, author) ->  map.putIfAbsent(author, this.books.values().stream().filter(b -> b.authorId == author.id).toList()), 
                    HashMap::putAll);
    }

    @SubscriptionMapping
    public Flux<Integer> stockprice () {
        return Flux.range(1, 60)
            .delayElements(Duration.ofSeconds(1));
    }
    
    @PostConstruct
    void load () {
        var author1 = new Author(1, "Müller");
        var author2 = new Author(2, "Mathias");

        authors.put(author1.id, author1);
        authors.put(author2.id, author2);
        
        books.put(1l,  new Book(1, "title1",  "summary1", author1.id));    
        books.put(2l,  new Book(2, "title2",  "summary2", author1.id));    
        books.put(3l,  new Book(3, "title3",  "summary3", author2.id));  
    }

    @Scheduled(fixedRate =  300_000L)
    void  clients () {    
        //
        var authorQuery = 
        """
            {
                author (id: 4) {
                    id
                    name
                    books {
                        id 
                        title
                        summary
                    }
                }
            }                
        """;

        var stockPriceSubscription = 
        """
            subscription {
                stockprice
            }        
        """;
        
        syncClient. document(authorQuery)
            .retrieve("author")
            .toEntity(AuthorWithBooks.class)
            .doOnError(ex -> logger.error("xxxx error occured.", ex))
            .subscribe(author -> logger.info("----> author : {}", author));

    //    wsClient.document(stockPriceSubscription)
    //         .retrieveSubscription("stockprice")
    //         .toEntity(Integer.class)
    //         .subscribe(price ->  logger.info(" ----> price: {}", price));     
    }


    @GraphQlExceptionHandler
    public GraphQLError handle (NotFoundAuthorException ex) {
        return GraphqlErrorBuilder.newError()
        .message(ex.getMessage())
        .errorType(org.springframework.graphql.execution.ErrorType.NOT_FOUND)
        .build();
    }

    record Book (long id, String title, String summary, long authorId) {}

    record Author (long id, String name) {}

    record BookWithAuthor (long id,  String title, String summary, Author author) {}

    record AuthorWithBooks (long id, String name, List<Book> books) {}
    
    class NotFoundAuthorException extends RuntimeException {
        public NotFoundAuthorException(String message) {
            super(message);
        }
    }
}


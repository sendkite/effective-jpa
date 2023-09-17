package com.example.bookstore;

import com.example.bookstore.service.BookstoreService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookstoreApplication {

    private final BookstoreService bookstoreService;

    public BookstoreApplication(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {
            System.out.println("\nInsert author with books  ...");
            bookstoreService.insertAuthorWithBooks();

            System.out.println("\nDelete a book of an author...");
            bookstoreService.deleteBookOfAuthor();
        };
    }
}

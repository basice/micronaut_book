package com.example;

import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.Micronaut;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;
import java.util.List;
import javax.transaction.Transactional;

@Singleton
public class Application {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public Application(
        BookRepository bookRepository,
        AuthorRepository authorRepository
    ) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }

    @EventListener
    @Transactional
    void startup(StartupEvent event) {
        Author savedAuthor = authorRepository.save(
            new Author(null, "Stephen King")
        );

        bookRepository.saveAll(
            List.of(
                new Book("The Stand", 1000, savedAuthor),
                new Book("The Shining", 500, savedAuthor)
            )
        );
    }
}

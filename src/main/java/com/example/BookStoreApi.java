package com.example;

import io.micronaut.data.repository.CrudRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

public sealed interface BookStoreApi {

    @Get("/describe/{type}/{id}")
    String describe(String type, Long id);

    non-sealed interface BookClient extends BookStoreApi {
    }

    @Controller("/")
    final class BookController implements BookStoreApi {

        private final BookRepository bookRepository;
        private final AuthorRepository authorRepository;

        public BookController(BookRepository bookRepository, AuthorRepository authorRepository) {
            this.bookRepository = bookRepository;
            this.authorRepository = authorRepository;
        }

        @Override
        public String describe(String type, Long id) {
            CrudRepository<? extends Entity<Long>, Long> crudRepository = switch (type.toLowerCase()) {
                case "book" -> this.bookRepository;
                case "author" -> this.authorRepository;
                default -> null;
            };
            if (crudRepository != null) {
                Entity<Long> entity = crudRepository.findById(id).orElse(null);

                // pattern switch is still currently in preview mode
                /* if (entity != null) {
                    return switch (entity) {
                        case Book b && b.enabled() -> b.title();
                        case Author a -> a.name();
                        default -> null;
                    };
                } */

                if (entity instanceof Book b && b.enabled()) {
                    return b.title();
                } else if (entity instanceof Author a) {
                    return a.name();
                }
            }
            return null; // will return a 404
        }
    }
}
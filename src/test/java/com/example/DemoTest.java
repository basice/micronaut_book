package com.example;

import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
class DemoTest {

    @Inject
    AuthorRepository authorRepository;

    @Inject
    BookRepository bookRepository;

    @Test
    void testItWorks(BookStoreApi.BookClient client) {
        Author author = authorRepository.findAll().iterator().next();
        Assertions.assertNotNull(author);

        String result = client.describe("author", author.id());
        Assertions.assertEquals("Stephen King", result);
    }

    @Client("/")
    interface TestBookStoreClient extends BookStoreApi.BookClient {}
}

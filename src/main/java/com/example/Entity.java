package com.example;

public sealed interface Entity<ID> permits Author, Book {
    ID id();
}

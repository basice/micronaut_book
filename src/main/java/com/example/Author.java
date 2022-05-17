package com.example;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

import javax.validation.constraints.NotBlank;

@MappedEntity
public record Author(
    @GeneratedValue @Id @Nullable Long id,
    @NotBlank String name
)
    implements Entity<Long> {
}

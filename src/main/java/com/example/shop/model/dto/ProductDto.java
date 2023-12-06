package com.example.shop.model.dto;

import jakarta.validation.constraints.*;

public record ProductDto(

        @NotBlank(message = "Name must not be empty")
        @Size(min = 2)
        String name,

        @NotBlank(message = "Description must not be empty")
        String description,

        @Positive(message = "Price must be greater than 0")
        double price)
{
}

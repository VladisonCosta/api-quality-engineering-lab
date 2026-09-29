package br.com.vladison.qa.product;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "name is required")
        @Size(max = 120, message = "name must contain at most 120 characters")
        String name,

        @NotBlank(message = "sku is required")
        @Pattern(regexp = "^[A-Z0-9-]{3,40}$", message = "sku must use uppercase letters, numbers or hyphens")
        String sku,

        @NotNull(message = "price is required")
        @DecimalMin(value = "0.01", message = "price must be greater than zero")
        BigDecimal price,

        @NotNull(message = "stock is required")
        @Min(value = 0, message = "stock cannot be negative")
        Integer stock
) {}

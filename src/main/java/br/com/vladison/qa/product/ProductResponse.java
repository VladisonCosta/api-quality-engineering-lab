package br.com.vladison.qa.product;

import java.math.BigDecimal;

public record ProductResponse(Long id, String name, String sku, BigDecimal price, Integer stock) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(), product.getName(), product.getSku(), product.getPrice(), product.getStock());
    }
}

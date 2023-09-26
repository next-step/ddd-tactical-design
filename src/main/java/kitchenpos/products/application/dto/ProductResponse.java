package kitchenpos.products.application.dto;

import java.util.UUID;

import kitchenpos.products.domain.Product;

public class ProductResponse {
    private UUID id;
    private String name;
    private Long price;

    public ProductResponse(final UUID id, final String name, final Long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public ProductResponse(final Product product) {
        this(product.getId(), product.getName().stringValue(), product.getPrice().longValue());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }
}

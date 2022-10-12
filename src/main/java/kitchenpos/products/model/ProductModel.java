package kitchenpos.products.model;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductModel {

    private UUID id;

    private String name;

    private BigDecimal price;

    public ProductModel(UUID id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public ProductModel(String name, BigDecimal price) {
        this(UUID.randomUUID(), name, price);
    }

    public ProductModel() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    public BigDecimal price() {
        return price;
    }
}

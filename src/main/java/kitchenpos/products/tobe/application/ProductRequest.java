package kitchenpos.products.tobe.application;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class ProductRequest {
    private UUID id;

    private String name;

    private BigDecimal price;

    public ProductRequest() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public void validate(final boolean containsProfanity) {
        validatePrice();
        validateName(containsProfanity);
    }

    public void validatePrice() {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }

    private void validateName(final boolean containsProfanity) {
        if (Objects.isNull(name) || containsProfanity) {
            throw new IllegalArgumentException();
        }
    }
}

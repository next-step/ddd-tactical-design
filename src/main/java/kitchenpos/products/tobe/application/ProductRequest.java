package kitchenpos.products.tobe.application;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Predicate;

public class ProductRequest {

    private String name;

    private BigDecimal price;

    public ProductRequest() {
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

    public void validate(Predicate<String> predicate) {
        validatePrice();
        validateName(predicate);
    }

    public void validatePrice() {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
    }

    private void validateName(Predicate<String> predicate) {
        if (Objects.isNull(name) || predicate.test(name)) {
            throw new IllegalArgumentException();
        }
    }
}

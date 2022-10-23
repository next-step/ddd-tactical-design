package kitchenpos.menus.menu.tobe.domain.vo;

import java.util.Objects;
import java.util.UUID;

public class ProductSpecification {

    private final UUID id;
    private final Long price;

    public ProductSpecification(UUID id, Long price) {
        this.id = id;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public Long getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductSpecification product = (ProductSpecification) o;
        return Objects.equals(id, product.id) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price);
    }
}

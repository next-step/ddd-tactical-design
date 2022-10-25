package kitchenpos.products.ui.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class CreateProductRequest {
    public BigDecimal price;
    public String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateProductRequest that = (CreateProductRequest) o;
        return Objects.equals(price, that.price) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, name);
    }
}

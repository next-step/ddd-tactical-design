package kitchenpos.product.domain;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ProductId {
    private final UUID value;

    private ProductId(UUID value) {
        this.value = value;
    }

    public static ProductId from(final UUID value) {
        if (value == null) {
            return newId();
        }

        return new ProductId(value);
    }

    public static ProductId newId() {
        return new ProductId(null);
    }

    public UUID getValue() {
        return value;
    }
}

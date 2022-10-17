package kitchenpos.products.tobe.application.dto;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.global.exception.NonInstantiableException;
import kitchenpos.products.tobe.domain.model.entity.Product;

public final class ProductInfo {

    private ProductInfo() {
        throw new NonInstantiableException();
    }

    public static class Create {

        private final UUID id;
        private final String name;
        private final BigDecimal price;

        public Create(Product product) {
            this.id = product.getId();
            this.name = product.getName().toString();
            this.price = product.getPrice().getPrice();
        }

        public UUID getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public BigDecimal getPrice() {
            return price;
        }
    }

    public static class ChangePrice {

        private final UUID id;
        private final String name;
        private final BigDecimal price;

        public ChangePrice(Product product) {
            this.id = product.getId();
            this.name = product.getName().toString();
            this.price = product.getPrice().getPrice();
        }

        public UUID getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public BigDecimal getPrice() {
            return price;
        }
    }
}

package kitchenpos.product.domain.model;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.product.domain.entity.Product;

public record ProductVo() {

    public record ProductInfo(
        ProductId productId,
        ProductName name,
        ProductPrice price
    ) {
        public static ProductInfo fromEntity(Product entity) {
            return new ProductInfo(entity.getProductId(), entity.getName(), entity.getPrice());
        }

        public UUID getProductId() {
            return productId.get();
        }

        public String getProductName() {
            return name.get();
        }

        public BigDecimal getProductPrice() {
            return price.get();
        }
    }

    public record Create(String name, ProductPrice price) {}

    public record Update(ProductId productId, ProductPrice price) {}
}

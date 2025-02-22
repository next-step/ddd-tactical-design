package kitchenpos.product.domain.model;

import java.util.UUID;
import kitchenpos.product.domain.entity.Product;

public record ProductVo() {

    public record ProductInfo(
        UUID productId,
        ProductName name,
        ProductPrice price
    ) {
        public static ProductInfo fromEntity(Product entity) {
            return new ProductInfo(entity.getId(), entity.getName(), entity.getPrice());
        }
    }

    public record Create(String name, ProductPrice price) {}

    public record Update(UUID productId, ProductPrice price) {}
}

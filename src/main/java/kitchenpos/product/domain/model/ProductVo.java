package kitchenpos.product.domain.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.product.domain.entity.Product;

public record ProductVo() {

    public record ProductInfo(
        UUID productId,
        String name,
        BigDecimal price
    ) {
        public static ProductInfo fromEntity(Product entity) {
            return new ProductInfo(entity.getId(), entity.getName(), entity.getPrice());
        }
    }

    public record Create(String name, BigDecimal price) {
        public Create {
            if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException(ErrorCode.PRODUCT_PRICE_NOT_ALLOWED.toString());
            }
        }
    }

    public record Update(UUID productId, BigDecimal price) {
        public Update {
            if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException(ErrorCode.PRODUCT_PRICE_NOT_ALLOWED.toString());
            }
        }
    }
}

package kitchenpos.products.tobe.domain.vo;

import java.math.BigDecimal;
import java.util.Objects;

import kitchenpos.common.ValueObject;

@ValueObject
public final class ProductPrice {

    private BigDecimal price;

    private ProductPrice() {
    }

    public ProductPrice(final BigDecimal price) {
        validation(price);
        this.price = price;
    }

    private void validation(final BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("상품의 가격은 비어있거나, 0원 보다 작을 수 없습니다.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductPrice that = (ProductPrice) o;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }

}

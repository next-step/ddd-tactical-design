package kitchenpos.product.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ProductPrice {
    @Column(name = "price", nullable = false)
    private Long price;

    public ProductPrice(Long price) {
        validate(price);
        this.price = price;
    }

    protected ProductPrice() {}

    public long getPrice() {
        return price;
    }

    private void validate(Long price) {
        if (Objects.isNull(price)) {
            throw new IllegalArgumentException("상품 가격은 필수값입니다.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("상품 가격은 0보다 작을 수 없습니다.");
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ProductPrice that = (ProductPrice) object;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}

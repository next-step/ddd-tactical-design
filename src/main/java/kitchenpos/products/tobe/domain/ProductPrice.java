package kitchenpos.products.tobe.domain;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice {

    private static final BigDecimal MIN_PRICE = BigDecimal.ZERO;

    private BigDecimal price;

    protected ProductPrice() {
    }

    public ProductPrice(BigDecimal price) {
        validate(price);
        this.price = price;
    }

    private void validate(BigDecimal price) {
        if (price == null) {
            throw new IllegalArgumentException("상품 가격은 필수항목입니다. 상품가격을 확인해 주세요.");
        }
        if (price.compareTo(MIN_PRICE) < 0) {
            throw new IllegalArgumentException("상품의 가격은 " + MIN_PRICE + " 원 이상이어야 합니다. 상품가격을 확인해 주세요.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPrice that = (ProductPrice) o;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}

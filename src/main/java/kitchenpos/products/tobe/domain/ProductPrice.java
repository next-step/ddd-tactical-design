package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice {

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected ProductPrice() {
    }

    protected ProductPrice(BigDecimal price) {
        validation(price);

        this.price = price;
    }

    private void validation(BigDecimal price) {
        if (Objects.isNull(price)) {
            throw new IllegalArgumentException("상품 가격은 필수 입니다");
        }

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("가격은 음수가 될수 없습니다.");
        }
    }

    public BigDecimal getPrice() {
        return price;
    }
}

package kitchenpos.products.tobe.product.domain;

import kitchenpos.products.tobe.support.Value;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice extends Value<ProductPrice> {

    private static final String PRODUCT_PRICE_EMPTY_MESSAGE = "상품의 가격은 비어있을 수 없습니다.";
    private static final String PRODUCT_PRICE_NEGATIVE_MESSAGE = "상품의 가격은 음수일 수 없습니다.";

    @Column(name = "price", nullable = false)
    private BigDecimal price;


    public ProductPrice(BigDecimal price) {
        validate(price);
        this.price = price;
    }

    protected ProductPrice() { /* empty */ }

    private void validate(BigDecimal price) {
        if (Objects.isNull(price)) {
            throw new IllegalArgumentException(PRODUCT_PRICE_EMPTY_MESSAGE);
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(PRODUCT_PRICE_NEGATIVE_MESSAGE);
        }
    }

    public BigDecimal value() {
        return price;
    }
}

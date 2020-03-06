package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
class ProductPrice {
    @Column(name = "price", nullable = false)
    private Long price;

    protected ProductPrice() {}

    private ProductPrice(final Long price) {
        validate(price);
        this.price = price;
    }

    static ProductPrice valueOf(final Long price) {
        return new ProductPrice(price);
    }

    private void validate(final Long price) {
        if(price == null || price < 0) {
            throw new IllegalArgumentException("제품 가격은 0원 이상이여야한다.");
        }
    }

    Long toLong() {
        return price;
    }
}

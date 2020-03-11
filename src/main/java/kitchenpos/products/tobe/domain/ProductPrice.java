package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
class ProductPrice {
    @Column(name = "price", nullable = false)
    private Long price;

    protected ProductPrice() {
    }

    public ProductPrice(final Long price) {
        validate(price);
        this.price = price;
    }

    private void validate(final Long price) {
        if (price == null || price < 0) {
            throw new IllegalArgumentException("제품 가격은 0원 이상이여야한다.");
        }
    }

    Long toLong() {
        return price;
    }
}

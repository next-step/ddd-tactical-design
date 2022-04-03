package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductPrice {

    @Column(name = "price", nullable = false)
    private Long price;

    protected ProductPrice() {
    }

    public ProductPrice(Long price) {
        if (price == null || price <= 0L) {
            throw new IllegalArgumentException("가격은 0원 이하가 될 수 없습니다.");
        }

        this.price = price;
    }

    public Long value() {
        return price;
    }
}

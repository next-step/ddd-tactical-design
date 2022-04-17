package common.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected Price() {
    }

    public Price(BigDecimal price) {
        validation(price);
        this.price = price;
    }

    private void validation(BigDecimal price) {
        if (Objects.isNull(price)) {
            throw new IllegalArgumentException("가격은 필수입니다.");
        }

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("가격은 0원 이상이여야 합니다.");
        }
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void validationTotalPrice(BigDecimal sum) {
        if (price.compareTo(sum) > 0) {
            throw new IllegalArgumentException("가격은 메뉴에 포함된 상품의 가격의 총합보다 작거나 같아야한다.");
        }
    }
}

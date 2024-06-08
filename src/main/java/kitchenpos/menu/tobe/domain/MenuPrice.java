package kitchenpos.menu.tobe.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.exception.IllegalPriceException;

@Embeddable
public class MenuPrice {
    @Column(name = "price", nullable = false)
    private long price;

    protected MenuPrice() {
    }

    public MenuPrice(Long price) {
        validatePrice(price);
        this.price = price;
    }

    private static void validatePrice(Long price) {
        if (price < 0) {
            throw new IllegalPriceException("가격은 0원 미만일 수 없습니다. ", price);
        }
    }

    public Long getPrice() {
        return price;
    }
}

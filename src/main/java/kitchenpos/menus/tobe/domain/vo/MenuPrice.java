package kitchenpos.menus.tobe.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuPriceException;

import java.util.Objects;

@Embeddable
public class MenuPrice {
    @Column(name = "price", nullable = false)
    private int price;

    protected MenuPrice() {
    }

    public MenuPrice(int price) {
        this.price = checkMenuPrice(price);
    }

    public int checkMenuPrice(final int price) {
        if (Objects.isNull(price)) {
            throw new InvalidMenuPriceException("메뉴 가격은 존재해야 합니다.");
        }

        if (price < 0) {
            throw new InvalidMenuPriceException("메뉴 가격은 0보다 커야 합니다.");
        }
        return price;

    }
}

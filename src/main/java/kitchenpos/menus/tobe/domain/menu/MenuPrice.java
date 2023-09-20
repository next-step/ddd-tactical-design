package kitchenpos.menus.tobe.domain.menu;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuPrice {
    @Column(name = "price", nullable = false)
    private BigDecimal menuPrice;

    public BigDecimal getMenuPrice() {
        return menuPrice;
    }

    public MenuPrice(BigDecimal menuPrice) {
        validateMenuPrice(menuPrice);
        this.menuPrice = menuPrice;
    }

    protected MenuPrice() {
    }

    private void validateMenuPrice(BigDecimal menuPrice) {
        if (Objects.isNull(menuPrice)) {
            throw new IllegalArgumentException("메뉴 가격은 존재해야 합니다.");
        }
        if (menuPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("메뉴 가격이 0원 미만일 수 없습니다");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuPrice menuPrice1 = (MenuPrice) o;
        return Objects.equals(menuPrice, menuPrice1.menuPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuPrice);
    }
}

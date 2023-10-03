package kitchenpos.menus.tobe.domain.menu;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuProductPrice {
    @Column(name = "price", nullable = false)
    private BigDecimal menuProductPrice;

    protected MenuProductPrice() {
    }

    public MenuProductPrice(BigDecimal menuProductPrice) {
        validateMenuProductPrice(menuProductPrice);
        this.menuProductPrice = menuProductPrice;
    }

    private void validateMenuProductPrice(BigDecimal menuProductPrice) {
        if (Objects.isNull(menuProductPrice)) {
            throw new IllegalArgumentException("메뉴 상풍 가격은 필수로 존재해야 합니다.");
        }
        if (menuProductPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("메뉴 상품 가격은 0원 이상이어야 합니다.");
        }
    }

    public BigDecimal getMenuProductPrice() {
        return menuProductPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProductPrice that = (MenuProductPrice) o;
        return Objects.equals(menuProductPrice, that.menuProductPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuProductPrice);
    }
}

package kitchenpos.eatinorders.domain.tobe.domain;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class EatInOrderMenu {
    @Column(name = "menu_id", columnDefinition = "binary(16)")
    private UUID menuId;
    @Column(name = "menu_price")
    private EatInOrderMenuPrice menuPrice;

    protected EatInOrderMenu() {
    }

    public EatInOrderMenu(UUID menuId, EatInOrderMenuPrice menuPrice) {
        if (menuId == null) {
            throw new IllegalArgumentException("메뉴가 없으면 등록할 수 없습니다.");
        }
        if (menuPrice == null) {
            throw new IllegalArgumentException("메뉴 가격이 없으면 등록할 수 없습니다.");
        }
        this.menuId = menuId;
        this.menuPrice = menuPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EatInOrderMenu that = (EatInOrderMenu)o;
        return Objects.equals(menuId, that.menuId) && Objects.equals(menuPrice, that.menuPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId, menuPrice);
    }

    public EatInOrderMenuPrice menuPrice() {
        return menuPrice;
    }
}

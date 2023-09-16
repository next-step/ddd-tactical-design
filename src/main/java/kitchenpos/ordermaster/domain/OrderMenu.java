package kitchenpos.ordermaster.domain;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderMenu {
    @Column(name = "menu_id", columnDefinition = "binary(16)")
    private UUID menuId;
    @Column(name = "menu_price")
    private BigDecimal menuPrice;

    protected OrderMenu() {
    }

    public OrderMenu(UUID menuId, BigDecimal menuPrice) {
        if (menuId == null) {
            throw new IllegalArgumentException("메뉴가 없으면 등록할 수 없습니다.");
        }
        if (menuPrice == null) {
            throw new IllegalArgumentException("메뉴 가격이 없으면 등록할 수 없습니다.");
        }
        this.menuId = menuId;
        this.menuPrice = menuPrice;
    }
}

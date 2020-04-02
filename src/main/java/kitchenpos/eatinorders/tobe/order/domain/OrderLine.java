package kitchenpos.eatinorders.tobe.order.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Embeddable
public class OrderLine {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seq;

    @Column(name = "menu_id", nullable = false)
    private Long menuId;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected OrderLine() {
    }

    public OrderLine(final Long menuId, final long quantity) {
        validateMenuId(menuId);
        validateQuantity(quantity);
        this.menuId = menuId;
        this.quantity = quantity;
    }

    private void validateMenuId(final Long menuId) {
        if (menuId == null || menuId < 1) {
            throw new IllegalArgumentException("메뉴가 올바르지 않습니다.");
        }
    }

    private void validateQuantity(final long quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("수량이 올바르지 않습니다.");
        }
    }

    Long getSeq() {
        return seq;
    }

    Long getMenuId() {
        return menuId;
    }

    long getQuantity() {
        return quantity;
    }


}

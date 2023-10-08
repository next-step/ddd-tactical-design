package kitchenpos.order.tobe.eatinorder.domain;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.common.Price;
import kitchenpos.menu.tobe.domain.Menu;
import kitchenpos.order.tobe.eatinorder.application.dto.EatInOrderLineItemDto;

@Table(name = "eat_in_order_line_item")
@Entity
public class EatInOrderLineItem {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Column(name = "menu_id", columnDefinition = "binary(16)", nullable = false)
    private UUID menuId;

    @Embedded
    private Price price;

    protected EatInOrderLineItem() {
    }

    public EatInOrderLineItem(long quantity, UUID menuId, Price price) {
        this.quantity = quantity;
        this.menuId = menuId;
        this.price = price;
    }

    public static EatInOrderLineItem from(EatInOrderLineItemDto dto, Map<UUID, Menu> menus) {
        if (!menus.containsKey(dto.getMenuId())) {
            throw new IllegalArgumentException("존재하지 않는 메뉴 id입니다. menuId = ." + dto.getMenuId());
        }

        var menu = menus.get(dto.getMenuId());
        if (menu.isHide()) {
            throw new IllegalStateException("숨김 메뉴는 주문할 수 없습니다.");
        }

        if (menu.getPrice().compareTo(dto.getPrice()) != 0) {
            throw new IllegalArgumentException(
                "메뉴의 가격과 주문 메뉴의 가격이 일치하지 않습니다. menuPrice = " + menu.getPrice() + " orderMenuPrice = " + dto.getPrice());
        }

        return EatInOrderLineItem.from(dto);
    }

    private static EatInOrderLineItem from(EatInOrderLineItemDto dto) {
        return new EatInOrderLineItem(dto.getQuantity(), dto.getMenuId(), Price.of(dto.getPrice()));
    }

    public Long getSeq() {
        return seq;
    }

    public long getQuantity() {
        return quantity;
    }

    public UUID getMenuId() {
        return this.menuId;
    }

    public BigDecimal getPrice() {
        return this.price.getValue();
    }
}

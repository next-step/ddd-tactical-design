package kitchenpos.eatinorders.tobe.domain.entity;

import jakarta.persistence.*;
import kitchenpos.eatinorders.tobe.domain.vo.MenuInEatInOrders;
import kitchenpos.eatinorders.tobe.domain.vo.Price;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "order_line_item2")
@Entity(name = "OrderLineItem2")
public class OrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Embedded
    private Price price;

    @Embedded
    private MenuInEatInOrders menuInOrders;

    protected OrderLineItem() {}

    public OrderLineItem(Long seq, long quantity, BigDecimal price,
                         UUID menuId, BigDecimal menuPrice) {
        this(seq, quantity, price, menuId, false, menuPrice);
    }

    public OrderLineItem(Long seq, long quantity, BigDecimal price,
                         UUID menuId, boolean isDisplayedMenu, BigDecimal menuPrice) {
        this.seq = seq;
        this.quantity = quantity;
        this.price = new Price(price);
        menuInOrders = new MenuInEatInOrders(menuId, isDisplayedMenu, menuPrice);
        checkIsEqualPrice();
    }

    private void checkIsEqualPrice() {
        if (this.price.isNotSamePrice(menuInOrders.getPrice())) {
            throw new IllegalArgumentException("주문 항목 금액과 메뉴 금액이 서로 다릅니다.");
        }
    }

    public void updateMenuInOrders(UUID menuId, BigDecimal price, boolean displayed) {
        if (menuInOrders.isSameId(menuId)) {
            menuInOrders.update(price, displayed);
            checkIsEqualPrice();
        }
    }

    public Long getSeq() {
        return seq;
    }

    public long getQuantity() {
        return quantity;
    }

    public Price getPrice() {
        return price;
    }

    public UUID getMenuId() {
        return menuInOrders.getMenuId();
    }
}

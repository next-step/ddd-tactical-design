package kitchenpos.eatinorders.tobe.domain.entity;

import jakarta.persistence.*;
import kitchenpos.eatinorders.tobe.domain.vo.Price;

import java.math.BigDecimal;
import java.util.Objects;
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

    @Transient
    private UUID menuId;

    protected OrderLineItem() {}

    public OrderLineItem(Long seq, long quantity, BigDecimal price, UUID menuId) {
        this.seq = seq;
        this.quantity = quantity;
        this.price = new Price(price);
        this.menuId = checkMenuId(menuId);
    }

    private UUID checkMenuId(UUID menuId) {
        if (Objects.isNull(menuId)) {
            throw new IllegalArgumentException("메뉴 식별자가 존재하지 않습니다.");
        }
        return menuId;
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
        return menuId;
    }
}

package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.menus.domain.Menu;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class EatInOrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "menu_id",
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_order_line_item_to_menu")
    )
    private EatInMenu menu;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Transient
    private Price price;

    public EatInOrderLineItem() {
    }

    public EatInOrderLineItem(long seq, EatInMenu eatInMenu, long quantity, Price price) {
        this.seq = seq;
        this.menu = eatInMenu;
        this.quantity = quantity;
        this.price = price;
    }


    public static EatInOrderLineItem create(long seq, EatInMenu eatInMenu, long quantity, Price price) {
        return new EatInOrderLineItem(seq, eatInMenu, quantity, price);
    }
}

package kitchenpos.eatinorders.tobe.domain.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import kitchenpos.menus.tobe.domain.model.MenuPrice;

@Table(name = "order_line_item")
@Entity(name = "tobeOrderLineItem")
public class OrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

//    @ManyToOne(optional = false)
//    @JoinColumn(
//        name = "menu_id",
//        columnDefinition = "varbinary(16)",
//        foreignKey = @ForeignKey(name = "fk_order_line_item_to_menu")
//    )
//    private Menu menu;

    @JoinColumn(
        name = "menu_id",
        columnDefinition = "varbinary(16)",
        foreignKey = @ForeignKey(name = "fk_order_line_item_to_menu")
    )
    private UUID menuId;

    @Transient
    private MenuPrice price;

    @Embedded
    private OrderQuantity quantity;

    protected OrderLineItem() {
    }

    public OrderLineItem(final UUID menuId, final MenuPrice menuPrice, final OrderQuantity quantity) {
        this.menuId = menuId;
        this.price = menuPrice;
        this.quantity = quantity;
    }

}

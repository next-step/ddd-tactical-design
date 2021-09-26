package kitchenpos.eatinorders.tobe.domain.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.model.MenuPrice;

@Table(name = "order_line_item")
@Entity(name = "tobeOrderLineItem")
public class OrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "menu_id",
        columnDefinition = "varbinary(16)",
        foreignKey = @ForeignKey(name = "fk_order_line_item_to_menu")
    )
    private Menu menu;

    @Transient
    private MenuPrice price;

    @Embedded
    private OrderQuantity quantity;

    protected OrderLineItem() {
    }

    public OrderLineItem(final Menu menu, final MenuPrice menuPrice, final OrderQuantity quantity) {
        this.menu = menu;
        this.price = menuPrice;
        this.quantity = quantity;
    }

}

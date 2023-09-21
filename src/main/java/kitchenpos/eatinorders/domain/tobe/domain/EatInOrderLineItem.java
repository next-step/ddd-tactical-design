package kitchenpos.eatinorders.domain.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "eat_in_order_line_item")
@Entity
public class EatInOrderLineItem {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;
    @Embedded
    private EatInOrderMenu eatInOrderMenu;
    @Embedded
    private EatInOrderQuantity quantity;

    protected EatInOrderLineItem() {
    }

    public EatInOrderLineItem(EatInOrderMenu eatInOrderMenu, EatInOrderQuantity quantity) {
        this.eatInOrderMenu = eatInOrderMenu;
        this.quantity = quantity;
    }

    public EatInOrderMenuPrice menuPrice() {
        return eatInOrderMenu.menuPrice()
            .multiply(quantity.value());
    }

}

package kitchenpos.eatinorders.tobe.domain;

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
import kitchenpos.menus.tobe.domain.Menu;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {

    @Id
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "menu_id",
        columnDefinition = "varbinary(16)",
        foreignKey = @ForeignKey(name = "fk_order_line_item_to_menu")
    )
    private Menu menu;

    @Embedded
    private Stock quantity;

    protected OrderLineItem() { }

    private OrderLineItem(Menu menu, Stock quantity) {
        validMenu(menu);
        this.menu = menu;
        this.quantity = quantity;
    }

    private OrderLineItem(Menu menu, Long quantity) {
        this(menu, new Stock(quantity));
    }

    private void validMenu(Menu menu) {
        if (menu == null || !menu.isDisplayed()) {
            throw new InvalidOrderLineException("메뉴가 유효하지 않거나 판매중인 메뉴가 아닙니다.");
        }
    }

    public static OrderLineItem create(Menu menu, Stock quantity) {
        return new OrderLineItem(menu, quantity);
    }

    public static OrderLineItem create(Menu menu, Long quantity) {
        return new OrderLineItem(menu, quantity);
    }

    public Long getSeq() {
        return seq;
    }

    public Long getPrice() {
        return menu.getPrice();
    }

    public Long getQuantity() {
        return quantity.value();
    }

    public long getOrderLinePrice() {
        return getPrice() * getQuantity();
    }
}

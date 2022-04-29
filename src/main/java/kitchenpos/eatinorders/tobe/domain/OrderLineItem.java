package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {
    private static final String MENU_MUST_NOT_BE_NULL = "메뉴는 빈 값이 아니어야 합니다.";
    private static final String MENU_MUST_NOT_BE_HIDDEN = "숨겨진 메뉴로는 생성할 수 없습니다.";
    private static final String PRICE_MUST_BE_SAME_AS_MENU_PRICE = "메뉴 가격과 다르면 생성할 수 없습니다.";

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

    @Embedded
    private Quantity quantity;

    @Transient
    private BigDecimal price;

    protected OrderLineItem() {
    }

    public OrderLineItem(Menu menu, long quantity, BigDecimal price) {
        validate(menu, price);
        this.menu = menu;
        this.quantity = new Quantity(quantity);
        this.price = price;
    }

    private void validate(Menu menu, BigDecimal price) {
        if (Objects.isNull(menu)) {
            throw new IllegalArgumentException(MENU_MUST_NOT_BE_NULL);
        }
        if (!menu.isDisplayed()) {
            throw new IllegalStateException(MENU_MUST_NOT_BE_HIDDEN);
        }
        if (!price.equals(menu.getPrice())) {
            throw new IllegalArgumentException(PRICE_MUST_BE_SAME_AS_MENU_PRICE);
        }
    }
}

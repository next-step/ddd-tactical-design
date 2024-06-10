package kitchenpos.eatinorders.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.menus.domain.Menu;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @JoinColumn(
            name = "menu_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_order_line_item_to_menu")
    )
    private UUID id;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private int price;

    public OrderLineItem(UUID id, int quantity, int price) {
        checkQuantity(quantity);
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    protected OrderLineItem() {

    }

    private void checkQuantity(int quantity){
        if(quantity < 0){
            throw new IllegalArgumentException("수량이 음수이하일 수 없습니다.");
        }
    }
}

package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "order_line_item")
@Entity
public class TobeOrderLineItem {
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

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Transient
    private BigDecimal price;

    protected TobeOrderLineItem() {
    }

    public TobeOrderLineItem(Long seq, Menu menu, long quantity, BigDecimal price) {
        this.seq = seq;
        this.menu = menu;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getSeq() {
        return seq;
    }

    public Menu getMenu() {
        return menu;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }
}

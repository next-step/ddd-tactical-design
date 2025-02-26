package kitchenpos.order.common.domain.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import kitchenpos.menu.domain.model.MenuId;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

//    @ManyToOne(optional = false)
//    @JoinColumn(
//        name = "menu_id",
//        columnDefinition = "binary(16)",
//        foreignKey = @ForeignKey(name = "fk_order_line_item_to_menu")
//    )
//    private Menu menu;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "menu_id"))
    private MenuId menuId;

    @Transient
    private BigDecimal price;

    public OrderLineItem() {
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(final Long seq) {
        this.seq = seq;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(final long quantity) {
        this.quantity = quantity;
    }

    public MenuId getMenuId() {
        return menuId;
    }

    public void setMenuId(MenuId menuId) {
        this.menuId = menuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }
}

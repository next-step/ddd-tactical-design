package kitchenpos.eatinorders.tobe.domain.eatinorder;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.menu.MenuId;

@Table(name = "order_line_item")
@Entity(name = "TobeEatInOrderLineItem")
public class EatInOrderLineItem {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "seq"))
    private EatInOrderLineItemSeq seq;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "menu_id"))
    private MenuId menuId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "quantity"))
    private EatInOrderLineItemQuantity quantity;

    public EatInOrderLineItem(Long seq, UUID menuId, long quantity) {
        this.seq = new EatInOrderLineItemSeq(seq);
        this.quantity = new EatInOrderLineItemQuantity(quantity);
        this.menuId = new MenuId(menuId);
    }

    public EatInOrderLineItem(UUID menuId, long quantity) {
        this.menuId = new MenuId(menuId);
        this.quantity = new EatInOrderLineItemQuantity(quantity);
    }

    public EatInOrderLineItem(UUID menuId, BigDecimal price,
        MenuValidationService menuValidationService,
        long quantity) {
        menuValidationService.validateDisplayedAndPrice(menuId, price);
        this.menuId = new MenuId(menuId);
        this.quantity = new EatInOrderLineItemQuantity(quantity);
    }

    protected EatInOrderLineItem() {
    }

    public Long getSeq() {
        return seq.getValue();
    }


    public long getQuantity() {
        return quantity.getValue();
    }

    public UUID getMenuId() {
        return menuId.getValue();
    }
}

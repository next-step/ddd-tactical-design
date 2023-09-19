package kitchenpos.menus.domain;


import kitchenpos.menus.domain.vo.MenuProductQuantity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {

    @Id
    private UUID id;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "quantity", nullable = false)
    private long quantity;


    public MenuProduct(UUID productId, BigDecimal price, long quantity) {
        this.id = productId;
        this.price = price;
        this.quantity = new MenuProductQuantity(quantity).getQuantity();
    }

    public MenuProduct() {

    }

    public UUID getId() {
        return id;
    }

    public BigDecimal amount() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}

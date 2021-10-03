package kitchenpos.menus.tobe.domain.model;

import kitchenpos.common.domain.model.Price;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class MenuProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID productId;

    @Embedded
    private Price price;

    @Embedded
    private MenuQuantity menuQuantity;

    public MenuProduct(UUID productId, Price price, MenuQuantity menuQuantity) {
        this.productId = productId;
        this.price = price;
        this.menuQuantity = menuQuantity;
    }

    public MenuProduct() {

    }

    public UUID getProductId() {
        return this.productId;
    }

    public BigDecimal getAmount() {
        return price.getValue().multiply(menuQuantity.getQuantity());
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}

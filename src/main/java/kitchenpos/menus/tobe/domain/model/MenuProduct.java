package kitchenpos.menus.tobe.domain.model;

import kitchenpos.common.domain.model.Quantity;
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
    private Quantity quantity;

    public MenuProduct(UUID productId, Price price, Quantity quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public MenuProduct() {

    }

    public UUID getProductId() {
        return this.productId;
    }

    public BigDecimal getAmount() {
        return price.getValue().multiply(quantity.getValue());
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}

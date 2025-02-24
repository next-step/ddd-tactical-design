package kitchenpos.menu.tobe.domain.menu;

import jakarta.persistence.*;
import kitchenpos.common.exception.MenuException;

import java.util.Objects;
import java.util.UUID;

import static kitchenpos.common.exception.ErrorCode.*;


@Table(name = "menu_product")
@Entity
public class MenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "price", nullable = false)
    private Long price;
    @Column(name = "product_id", nullable = false)
    private UUID productId;

    protected MenuProduct() {}

    public MenuProduct(Long seq, int quantity, Long price, UUID productId) {
        validate(quantity, price);
        this.seq = seq;
        this.quantity = quantity;
        this.price = price;
        this.productId = productId;
    }

    private void validate(int quantity, Long price) {
        if (quantity < 0L) {
            throw new MenuException(MENU_QUANTITY_NEGATIVE);
        }
        if (price < 0L) {
            throw new MenuException(MENU_PRODUCT_PRICE_INVALID);
        }
    }

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void changePrice(Long price) {
        this.price = price;
    }

    public Long totalPrice() {
        return price * quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public UUID getProductId() {
        return productId;
    }

}

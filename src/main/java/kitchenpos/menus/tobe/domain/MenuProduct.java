package kitchenpos.menus.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.products.tobe.Money;

import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    private UUID productId;

    @Embedded
    private Money productPrice;

    public MenuProduct() {
    }

    public MenuProduct(UUID productId, long quantity, Money productPrice) {
        if (quantity < 0) {
            throw new IllegalArgumentException("수량은 0보다 작을 수 없습니다.");
        }
        this.quantity = quantity;
        this.productId = productId;
        this.productPrice = productPrice;
    }


    public void changePrice(Money productPrice) {
        this.productPrice = productPrice;
    }

    public Money amount() {
        return productPrice.multiply(quantity);
    }

    public UUID getProductId() {
        return productId;
    }
}

package kitchenpos.products.tobe.domain;

import kitchenpos.products.domain.Product;

import javax.persistence.*;
import java.util.UUID;


@Table(name = "menu_product")
@Entity
public class MenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;
    @Transient
    private UUID productId;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "quantity", nullable = false)
    private int quantity;


    protected MenuProduct() {
    }

    public MenuProduct(UUID productId, int price, int quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public int amount() {
        return price * quantity;
    }
}

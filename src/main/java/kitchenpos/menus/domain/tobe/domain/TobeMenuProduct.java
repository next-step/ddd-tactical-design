package kitchenpos.menus.domain.tobe.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "tobe_menu_product")
@Entity
public class TobeMenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "quantity", nullable = false)
    @Embedded
    private Quantity quantity;

    @Column(name = "price", nullable = false)
    @Embedded
    private Price price;

    @Transient
    private UUID productId;

    private TobeMenuProduct() {
    }

    public TobeMenuProduct(int quantity, int price, UUID productId) {
        this.quantity = new Quantity(quantity);
        this.price = new Price(price);
        this.productId = productId;
    }
}

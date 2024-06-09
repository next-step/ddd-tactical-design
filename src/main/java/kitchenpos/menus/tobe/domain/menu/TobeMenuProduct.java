package kitchenpos.menus.tobe.domain.menu;

import jakarta.persistence.*;
import kitchenpos.shared.domain.Price;

import java.util.UUID;

@Table(name = "tobe_menu_product")
@Entity
public class TobeMenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Embedded
    private Quantity quantity;

    @Embedded
    private Price price;

    @Transient
    private UUID productId;

    protected TobeMenuProduct() {
    }

    public TobeMenuProduct(int quantity, int price, UUID productId) {
        this.quantity = new Quantity(quantity);
        this.price = Price.of(price);
        this.productId = productId;
    }

    public int getTotalPrice() {
        return price.getPrice() * quantity.getQuantity();
    }
}

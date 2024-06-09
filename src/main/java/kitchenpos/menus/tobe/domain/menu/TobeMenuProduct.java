package kitchenpos.menus.tobe.domain.menu;

import jakarta.persistence.*;
import kitchenpos.menus.tobe.application.dto.TobeMenuProductResponse;
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
        return price.multiply(quantity);
    }

    public UUID getProductId() {
        return productId;
    }

    public TobeMenuProductResponse toDto() {
        return new TobeMenuProductResponse(seq, quantity.getQuantity(), price.getPrice(), productId);
    }
}

package kitchenpos.menu.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.exception.IllegalQuantityException;

import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long seq;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    private UUID productId;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    private long price;

    protected MenuProduct() {

    }

    public static MenuProduct of(final UUID productId, final long price,  final long quantity) {
        validateQuantity(quantity);
        return new MenuProduct(productId, price, quantity);
    }

    private MenuProduct(final UUID productId, final long price,  final long quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    private static void validateQuantity(long quantity) {
        if (quantity < 0) {
            throw new IllegalQuantityException("수량은 0개보다 적을 수 없습니다.");
        }
    }

    public long getMenuProductPrice() {
        return price * quantity;
    }


    public UUID getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public long getPrice() {
        return price;
    }

}

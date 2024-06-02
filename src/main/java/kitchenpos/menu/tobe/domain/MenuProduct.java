package kitchenpos.menu.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.exception.IllegalQuantityException;
import kitchenpos.product.tobe.domain.Product;

import java.math.BigDecimal;

@Table(name = "menu_product")
@Entity
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "product_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
    )
    private Product product;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    private BigDecimal price;

    protected MenuProduct() {

    }

    public MenuProduct(Product product, long quantity) {
        validateQuantity(quantity);
        this.product = product;
        this.quantity = quantity;
        calculateMenuProductPrice(product, BigDecimal.valueOf(quantity));
    }

    private static void validateQuantity(long quantity) {
        if (quantity < 0) {
            throw new IllegalQuantityException("수량은 0개보다 적을 수 없습니다.");
        }
    }

    private void calculateMenuProductPrice(Product product, BigDecimal quantity) {
        this.price = product.getProductPrice().multiply(quantity);
    }


    public Product getProduct() {
        return product;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

}

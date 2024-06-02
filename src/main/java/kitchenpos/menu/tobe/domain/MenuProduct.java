package kitchenpos.menu.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.product.tobe.domain.Product;

import java.math.BigDecimal;
import java.util.Objects;

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
    private int quantity;

    private BigDecimal price;

    public MenuProduct(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        calculateMenuProductPrice(product, BigDecimal.valueOf(quantity));
    }

    protected MenuProduct() {

    }

    private void calculateMenuProductPrice(Product product, BigDecimal quantity) {
        this.price = product.getProductPrice().multiply(quantity);
    }


    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

}

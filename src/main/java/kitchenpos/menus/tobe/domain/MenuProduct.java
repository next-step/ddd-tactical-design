package kitchenpos.menus.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "product_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
    )
    private Product product;

    @Transient
    private UUID productId;

    @Embedded
    private Quantity quantity;

    @Transient
    private Price price;

    protected MenuProduct() {}

    public MenuProduct(Long seq, Product product, long quantity) {
        this.seq = seq;
        this.product = product;
        this.quantity = new Quantity(quantity);
    }

    public MenuProduct(Long seq, long quantity, UUID productId, BigDecimal productPrice) {
        this.seq = seq;
        this.quantity = new Quantity(quantity);
        this.productId = productId;
        this.price = new Price(productPrice, quantity);
    }

    public BigDecimal totalPrice() {
        return price.getPrice();
    }

    public Long getSeq() {
        return seq;
    }

    public Product getProduct() {
        return product;
    }

    public long getQuantity() {
        return quantity.getQuantity();
    }
}

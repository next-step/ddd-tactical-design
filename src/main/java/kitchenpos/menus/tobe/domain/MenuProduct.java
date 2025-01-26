package kitchenpos.menus.tobe.domain;


import javax.persistence.*;
import java.math.BigDecimal;
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

    @Embedded
    private Product product;

    public static MenuProduct newOne(Product product, long quantity) {
        return new MenuProduct(null, quantity, product);
    }

    public MenuProduct() {
    }

    public MenuProduct(Long seq, long quantity, Product product) {
        this.seq = seq;
        this.quantity = quantity;
        this.product = product;
    }

    public UUID getProductId() {
        return this.product.getProductId();
    }

    public BigDecimal getProductPrice() {
        return this.product.getProductPrice().multiply(BigDecimal.valueOf(this.quantity));
    }

    public void updateProductPrice(BigDecimal productPrice) {
        this.product.updatePrice(productPrice);
    }
}

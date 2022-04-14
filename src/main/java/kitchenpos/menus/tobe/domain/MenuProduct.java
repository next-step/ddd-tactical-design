package kitchenpos.menus.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import kitchenpos.products.tobe.domain.Product;

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
        columnDefinition = "varbinary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
    )
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Quantity quantity;

    protected MenuProduct() { }

    private MenuProduct(Product product, Quantity quantity) {
        validProduct(product);
        this.product = product;
        this.quantity = quantity;
    }

    private void validProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("유효하지 않은 상품입니다.");
        }
    }

    public static MenuProduct create(Product product, Long quantity) {
        return new MenuProduct(product, new Quantity(quantity));
    }

    public long getPrice() {
        return product.getPrice() * quantity.value();
    }
}

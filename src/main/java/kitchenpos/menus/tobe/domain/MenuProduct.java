
package kitchenpos.menus.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.common.vo.PositiveNumber;
import kitchenpos.common.vo.Price;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductId;

import java.util.Objects;

@Entity(name = "menu_project")
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Embedded
    private ProductId productId;

    @Embedded
    private PositiveNumber quantity;

    @Embedded
    private Price productPrice;

    protected MenuProduct() {
    }

    public MenuProduct(Product product, int quantity) {
        this(product.getId(), new PositiveNumber(quantity), product.getPrice());
    }

    public MenuProduct(ProductId productId, int quantity, long productPrice) {
        this(productId, new PositiveNumber(quantity), new Price(productPrice));
    }

    public MenuProduct(ProductId productId, PositiveNumber quantity, Price productPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.productPrice = productPrice;
    }

    public Price amount() {
        return productPrice.multiply(quantity);
    }

    public ProductId getProductId() {
        return productId;
    }

    public PositiveNumber getQuantity() {
        return quantity;
    }

    public Price getProductPrice() {
        return productPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProduct that = (MenuProduct) o;
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(seq);
    }
}

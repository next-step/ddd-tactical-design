package kitchenpos.menus.tobe.domain;

import static java.util.Objects.isNull;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.global.vo.Price;
import kitchenpos.global.vo.Quantity;
import kitchenpos.menus.tobe.domain.vo.Product;

@Table(name = "tb_menu_product")
@Entity(name = "tb_menu_product")
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Embedded
    private Product product;

    @Embedded
    private Quantity quantity;

    protected MenuProduct() {
    }

    public MenuProduct(Product product, Quantity quantity) {
        validate(product.getId());
        this.product = product;
        this.quantity = quantity;
    }

    private void validate(UUID productId) {
        if (isNull(productId)) {
            throw new IllegalArgumentException("메뉴 상품은 반드시 하나의 상품을 갖는다.");
        }
    }

    public Product getProduct() {
        return product;
    }

    public MenuProduct withProduct(Product product) {
        this.product = product;
        return this;
    }

    public UUID getProductId() {
        return product.getId();
    }

    public Price getPrice() {
        Price productPrice = product.getPrice();
        return productPrice.multiply(quantity);
    }

    public Quantity getQuantity() {
        return quantity;
    }
}

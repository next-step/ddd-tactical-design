package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.products.tobe.domain.Product;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "tobe_menu_product")
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

    @Embedded
    private Quantity quantity;

    public MenuProduct() {

    }

    public MenuProduct(Product product, Quantity quantity) {
        validate(quantity);
        this.quantity = quantity;
        this.product = product;
    }

    private void validate(Quantity quantity) {
        if (!quantity.greaterThanZero()) {
            throw new IllegalArgumentException("메뉴에 속한 상품의 수량은 0이상 이어야 합니다.");
        }
    }

    public BigDecimal sum() {
        return this.product.price().multiply(this.quantity.quantity());
    }
}

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
import javax.persistence.Transient;
import kitchenpos.global.vo.Price;
import kitchenpos.global.vo.Quantity;

@Table(name = "tb_menu_product")
@Entity
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(
            name = "menu_id",
            columnDefinition = "binary(16)",
            nullable = false
    )
    private UUID menuId;

    @Column(
            name = "product_id",
            columnDefinition = "binary(16)",
            nullable = false
    )
    private UUID productId;

    @Embedded
    private Quantity quantity;

    @Transient
    private Price price;

    protected MenuProduct() {
    }

    public MenuProduct(UUID menuId, UUID productId, Quantity quantity, Price productPrice) {
        validate(menuId, productId);
        this.menuId = menuId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = productPrice.multiply(quantity);
    }

    private void validate(UUID menuId, UUID productId) {
        if (isNull(menuId)) {
            throw new IllegalArgumentException("메뉴 상품은 반드시 하나의 메뉴를 갖는다.");
        }

        if (isNull(productId)) {
            throw new IllegalArgumentException("메뉴 상품은 반드시 하나의 상품을 갖는다.");
        }
    }

    public Price getPrice() {
        return price;
    }
}

package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import kitchenpos.menus.tobe.domain.vo.MenuProductAmount;
import kitchenpos.menus.tobe.domain.vo.MenuProductQuantity;

@Table(name = "menu_product")
@Entity
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(
            name = "product_id",
            columnDefinition = "binary(16)",
            nullable = false
    )
    private UUID productId;

    @Embedded
    private MenuProductQuantity quantity;

    @Transient
    private MenuProductAmount amount;

    protected MenuProduct() {
    }

    public MenuProduct(UUID productId, MenuProductQuantity quantity, BigDecimal productPrice) {
        validate(productId);
        this.productId = productId;
        this.amount = new MenuProductAmount(productPrice, quantity);
        this.quantity = quantity;
    }

    private void validate(UUID productId) {
        if (Objects.isNull(productId)) {
            throw new IllegalArgumentException("메뉴 상품은 반드시 하나의 상품을 갖는다.");
        }
    }

    public MenuProductAmount getAmount() {
        return amount;
    }
}

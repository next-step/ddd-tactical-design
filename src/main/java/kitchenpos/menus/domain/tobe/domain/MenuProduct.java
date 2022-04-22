package kitchenpos.menus.domain.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
    private static final String QUANTITY_MUST_BE_POSITIVE_NUMBER = "수량은 0이상의 정수 이어야 합니다. 입력 값 : %s";

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(
            name = "product_id",
            columnDefinition = "varbinary(16)"
    )
    private UUID productId;

    @Transient
    private BigDecimal productPrice;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected MenuProduct() {
    }

    public MenuProduct(UUID productId, BigDecimal productPrice, long quantity) {
        validate(quantity);
        this.productId = productId;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    private void validate(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(String.format(QUANTITY_MUST_BE_POSITIVE_NUMBER, quantity));
        }
    }

    public long getAmount() {
        return productPrice.multiply(BigDecimal.valueOf(quantity)).longValue();
    }
}

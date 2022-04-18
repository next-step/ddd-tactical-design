package kitchenpos.menus.domain.tobe;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class MenuProduct {
    private static final String PRODUCT_ID_NULL_NOT_ALLOWED = "productId가 null 일 수 없습니다";

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Embedded
    private MenuQuantity quantity;

    private UUID productId;

    private MenuPrice price;

    protected MenuProduct() {
    }

    public MenuProduct(Long quantity, UUID productId, BigDecimal price) {
        this(null, quantity, productId, price);
    }

    public MenuProduct(Long seq, Long quantity, UUID productId, BigDecimal price) {
        validate(productId);
        this.seq = seq;
        this.quantity = new MenuQuantity(quantity);
        this.productId = productId;
        this.price = new MenuPrice(price);
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public Long getQuantity() {
        return quantity.getQuantity();
    }

    public MenuPrice getTotalPrice() {
        return price.multiply(getQuantity());
    }

    private void validate(UUID productId) {
        if (productId == null) {
            throw new IllegalArgumentException(PRODUCT_ID_NULL_NOT_ALLOWED);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuProduct)) {
            return false;
        }
        MenuProduct that = (MenuProduct) o;
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }
}

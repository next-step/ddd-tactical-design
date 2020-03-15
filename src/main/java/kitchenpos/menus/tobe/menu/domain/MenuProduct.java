package kitchenpos.menus.tobe.menu.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Embeddable
public class MenuProduct {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seq", nullable = false)
    private Long seq;
    @Column(name = "product_id", nullable = false)
    private Long productId;
    @Column(name = "quantity", nullable = false)
    private Long quantity;
    @Transient
    private BigDecimal price;

    protected MenuProduct() {
    }

    public MenuProduct(final Long productId, final BigDecimal price, final Long quantity) {
        validateProductId(productId);
        validatePrice(price);
        validateQuantity(quantity);
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    private void validateProductId(final Long productId) {
        if (productId == null) {
            throw new IllegalArgumentException("제품이 지정되어야합니다.");
        }
    }

    private void validatePrice(final BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("제품가격은 0원 이상이여야합니다.");
        }
    }

    private void validateQuantity(final Long quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("제품 개수는 1개 이상이여야합니다.");
        }
    }

    public Long getProductId() {
        return productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getQuantity() {
        return quantity;
    }
}

package kitchenpos.menus.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "product_id", length = 16, nullable = false, columnDefinition = "binary(16)")
    private UUID productId;

    @Embedded
    private MenuProductPrice price;

    @Embedded
    private MenuQuantity quantity;

    protected MenuProduct() {
    }

    public MenuProduct(UUID productId, MenuProductPrice price, MenuQuantity quantity) {
        if(quantity.lessThenOne()) {
            throw new IllegalArgumentException("메뉴에 속한 상품의 수량은 0이상 이어야 합니다.");
        }

        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getSeq() {
        return seq;
    }

    public MenuProductPrice getPrice() {
        return price;
    }

    public UUID getProductId() {
        return productId;
    }

    public MenuQuantity getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuProduct that = (MenuProduct) o;

        if (!Objects.equals(seq, that.seq)) return false;
        if (!Objects.equals(productId, that.productId)) return false;
        if (!Objects.equals(price, that.price)) return false;
        return Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        int result = seq != null ? seq.hashCode() : 0;
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        return result;
    }

    public BigDecimal calculatePrice() {
        return price.value().multiply(BigDecimal.valueOf(quantity.value()));
    }
}

package kitchenpos.menu.adapter.out.persistance.entity;

import jakarta.persistence.*;
import kitchenpos.menu.domain.model.MenuProduct;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProductEntity {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Column(name = "product_id", columnDefinition = "binary(16)", nullable = false, updatable = false)
    private UUID productId;

    @Column(name = "product_price", nullable = false)
    private BigDecimal productPrice;

    public MenuProductEntity() {
    }

    public static MenuProductEntity of(final MenuProduct menuProduct) {
        final MenuProductEntity menuProductEntity = new MenuProductEntity();
        menuProductEntity.setSeq(menuProduct.getSeq());
        menuProductEntity.setQuantity(menuProduct.getQuantity());
        menuProductEntity.setProductId(menuProduct.getProductId());
        menuProductEntity.setProductPrice(menuProduct.getProductPrice());
        return menuProductEntity;
    }

    public MenuProduct toDomain() {
        return new MenuProduct(this.seq, this.productId, this.quantity, this.productPrice);
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(final Long seq) {
        this.seq = seq;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(final long quantity) {
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(final UUID productId) {
        this.productId = productId;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MenuProductEntity that = (MenuProductEntity) o;
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(seq);
    }
}

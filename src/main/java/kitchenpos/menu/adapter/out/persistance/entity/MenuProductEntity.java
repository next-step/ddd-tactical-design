package kitchenpos.menu.adapter.out.persistance.entity;

import jakarta.persistence.*;
import kitchenpos.menu.domain.model.MenuProduct;
import kitchenpos.product.adapter.out.persistance.entity.ProductEntity;

import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProductEntity {
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
    private ProductEntity product;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    @Transient
    private UUID productId;

    public MenuProductEntity() {
    }

    public static MenuProductEntity of(final MenuProduct menuProduct) {
        final MenuProductEntity menuProductEntity = new MenuProductEntity();
        menuProductEntity.setSeq(menuProduct.getSeq());
        menuProductEntity.setQuantity(menuProduct.getQuantity());
        menuProductEntity.setProductId(menuProduct.getProductId());
        menuProductEntity.setProduct(ProductEntity.of(menuProduct.getProduct()));
        return menuProductEntity;
    }

    public MenuProduct toDomain() {
        final MenuProduct menuProduct = new MenuProduct();
        menuProduct.setSeq(this.seq);
        menuProduct.setQuantity(this.quantity);
        menuProduct.setProductId(this.productId);
        menuProduct.setProduct(this.product.toDomain(nm -> {}));
        return menuProduct;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(final Long seq) {
        this.seq = seq;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(final ProductEntity product) {
        this.product = product;
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
}

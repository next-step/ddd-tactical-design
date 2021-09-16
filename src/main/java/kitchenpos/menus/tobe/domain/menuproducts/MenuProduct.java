package kitchenpos.menus.tobe.domain.menuproducts;

import kitchenpos.products.tobe.domain.Product;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu_product")
@Entity(name = "TobeMenuProduct")
public class MenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "product_id",
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
    )
    private Product product;

    @Embedded
    private ProductQuantity productQuantity;

    @Transient
    private UUID productId;

    protected MenuProduct() {}

    public MenuProduct(final Product product, final ProductQuantity productQuantity) {
        if (Objects.isNull(product) || Objects.isNull(productQuantity)) {
            throw new IllegalArgumentException("상품과 상품 수량은 주문 목록의 필수값입니다.");
        }
        this.product = product;
        this.productQuantity = productQuantity;
        this.productId = product.getId();
    }

    public Long getSeq() {
        return seq;
    }

    public Product getProduct() {
        return product;
    }

    public long getQuantity() {
        return productQuantity.getQuantity();
    }

    public UUID getProductId() {
        return productId;
    }
}

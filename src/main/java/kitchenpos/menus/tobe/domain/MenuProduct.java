package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductId;

import javax.persistence.*;
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

    @AttributeOverrides(
            @AttributeOverride(name = "id", column = @Column(name = "product_id"))
    )
    private ProductId productId;

    @Embedded
    private Quantity quantity;

    @Transient
    private Price price;

    protected MenuProduct() {
    }

    public MenuProduct(final UUID productId, final Quantity quantity) {
        this(new ProductId(productId), quantity);
    }

    public MenuProduct(final ProductId productId, final Quantity quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId.getId();
    }

    public void loadProduct(final Product product) {
        this.price = product.getPrice();
    }

    public BigDecimal calculate() {
        if(Objects.isNull(price)) {
            throw new IllegalStateException("등록된 상품인지 확인해야합니다.");
        }
        return price.calculate(quantity);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final MenuProduct that = (MenuProduct) o;
        return Objects.equals(seq, that.seq) || (Objects.equals(productId, that.productId) && Objects.equals(quantity, that.quantity));
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, quantity);
    }
}

package kitchenpos.menus.tobe.domain.menuproduct;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.ProductPriceService;
import kitchenpos.products.tobe.domain.ProductId;

@Table(name = "menu_product")
@Entity(name = "TobeMenuProduct")
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "product_id"))
    private ProductId productId;


    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "quantity", nullable = false))
    private MenuProductQuantity quantity;

    public MenuProduct(Long seq, UUID productId, long quantity) {
        this.seq = seq;
        this.productId = new ProductId(productId);
        this.quantity = new MenuProductQuantity(quantity);
    }

    public MenuProduct(UUID productId, long quantity) {
        this.productId = new ProductId(productId);
        this.quantity = new MenuProductQuantity(quantity);
    }

    protected MenuProduct() {
    }

    public Long getSeq() {
        return seq;
    }


    public long getQuantity() {
        return quantity.getValue();
    }

    public UUID getProductId() {
        return productId.getValue();
    }

    public BigDecimal calculatePrice(ProductPriceService productPriceService) {
        return productPriceService.getPriceByProductId(this.productId.getValue())
            .multiply(BigDecimal.valueOf(getQuantity()));
    }
}

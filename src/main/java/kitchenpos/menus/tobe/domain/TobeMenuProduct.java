package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.util.UUID;

//@Table(name = "menu_product")
@Entity
public class TobeMenuProduct {

    @Id
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(name = "product_id", columnDefinition = "binary(16)", nullable = false)
    private UUID productId;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    protected TobeMenuProduct() {
    }

    private TobeMenuProduct(UUID productId, BigDecimal price, long quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public static TobeMenuProduct create(UUID productId, BigDecimal price, long quantity) {
        return new TobeMenuProduct(productId, price, quantity);
    }

    public Long seq() {return seq;
    }

    public UUID productId() {
        return productId;
    }

    public BigDecimal price() {
        return price;
    }

    public long quantity() {
        return quantity;
    }

    /**
     * 상품의 구성품의 총 가격을 계산합니다.
     *
     * @return the 계산된 가격 (수량*상품 가격)
     */
    public BigDecimal calculatedPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }
}

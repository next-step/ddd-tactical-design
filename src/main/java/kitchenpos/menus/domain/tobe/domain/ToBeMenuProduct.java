package kitchenpos.menus.domain.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tobe_menu_product")
@Entity
public class ToBeMenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "product_id")
    private UUID productId;
    @Column(name = "price")
    private MenuProductPrice price;
    @Embedded
    private Quantity quantity;

    public ToBeMenuProduct(UUID productId, long price, long quantity) {
        validationOfProduct(productId);
        this.productId = productId;
        this.price = MenuProductPrice.of(price);
        this.quantity = Quantity.of(quantity);
    }

    public ToBeMenuProduct(UUID productId, BigDecimal price, long quantity) {
        validationOfProduct(productId);
        this.productId = productId;
        this.price = MenuProductPrice.of(price);
        this.quantity = Quantity.of(quantity);
    }

    protected ToBeMenuProduct() {

    }

    private void validationOfProduct(UUID productId) {
        if (productId == null) {
            throw new IllegalArgumentException("상품정보가 입력되야 합니다.");
        }
    }

    public MenuProductPrice amount() {
        return price.multiply(quantity.getValue());
    }

    public void changePrice(BigDecimal value) {
        this.price = MenuProductPrice.of(value);
    }
}

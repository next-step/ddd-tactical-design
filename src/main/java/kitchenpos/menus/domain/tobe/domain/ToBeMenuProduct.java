package kitchenpos.menus.domain.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import kitchenpos.products.domain.tobe.domain.Price;
import kitchenpos.products.domain.tobe.domain.ToBeProduct;

@Table(name = "tobe_menu_product")
@Entity
public class ToBeMenuProduct {
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
    private ToBeProduct product;

    @Embedded
    private Quantity quantity;

    protected ToBeMenuProduct() {
    }

    public ToBeMenuProduct(ToBeProduct product, long quantity) {
        validationOfProduct(product);
        this.product = product;
        this.quantity = Quantity.of(quantity);
    }

    private void validationOfProduct(ToBeProduct product) {
        if (product == null) {
            throw new IllegalArgumentException("상품정보가 입력되야 합니다.");
        }
    }

    public Price getProductPrice() {
        return product.getPrice();
    }
}

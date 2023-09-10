package kitchenpos.products.domain.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tobe_product")
@Entity
public class ToBeProduct {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    private Price price;

    public ToBeProduct() {
    }

    public ToBeProduct(String name, BigDecimal productPrice, boolean containsProfanity) {
        validationOfName(name, containsProfanity);
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = Price.of(productPrice);
    }

    private void validationOfName(String name, boolean containsProfanity) {
        if (name == "" || Objects.isNull(name)) {
            throw new IllegalArgumentException("상품 이름은 필수로 입력되야 합니다.");
        }
        if (containsProfanity) {
            throw new IllegalArgumentException("상품 이름에 비속어가 포함되어 있습니다.");
        }
    }

    public void changePrice(BigDecimal productPrice) {
        price = price.changePrice(productPrice);
    }

    public boolean isSamePrice(BigDecimal productPrice) {
        return price.isSamePrice(Price.of(productPrice));
    }

    public boolean isGreaterThanPrice(BigDecimal productPrice) {
        return price.isGreaterThan(Price.of(productPrice));
    }

}

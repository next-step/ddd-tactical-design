package kitchenpos.products.domain.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import kitchenpos.menus.domain.tobe.domain.Name;

@Table(name = "tobe_product")
@Entity
public class ToBeProduct {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private Name name;

    @Embedded
    private Price price;

    protected ToBeProduct() {
    }

    public ToBeProduct(String name, BigDecimal productPrice, boolean containsProfanity) {
        validationOfProfanity(containsProfanity);
        this.id = UUID.randomUUID();
        this.name = Name.of(name);
        this.price = Price.of(productPrice);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ToBeProduct product = (ToBeProduct)o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private void validationOfProfanity(boolean containsProfanity) {
        if (containsProfanity) {
            throw new IllegalArgumentException("메뉴 이름에 비속어가 포함되어 있습니다.");
        }
    }

    public void changePrice(BigDecimal productPrice) {
        price = price.changePrice(productPrice);
    }

    public boolean isGreaterThanPrice(BigDecimal productPrice) {
        return price.isGreaterThan(Price.of(productPrice));
    }

    public Price getPrice() {
        return price;
    }
}

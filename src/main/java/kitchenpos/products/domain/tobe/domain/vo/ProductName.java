package kitchenpos.products.domain.tobe.domain.vo;

import kitchenpos.support.infra.profanity.Profanity;
import kitchenpos.support.vo.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName extends ValueObject<ProductName> {

    @Column(name = "name")
    private String name;

    public ProductName(String name, Profanity profanity) {
        if(Objects.isNull(name) || "".equals(name) ||profanity.containsProfanity(name)) {
            throw new IllegalArgumentException("올바른 상품명이 아닙니다.");
        }
        this.name = name;
    }

    protected ProductName() {

    }

    public String getValue() {
        return name;
    }
}

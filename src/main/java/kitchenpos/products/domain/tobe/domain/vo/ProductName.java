package kitchenpos.products.domain.tobe.domain.vo;

import kitchenpos.products.domain.tobe.domain.policy.ProductNamingRule;
import kitchenpos.products.exception.ProductNamingRuleViolationException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {

    @Column(name = "name")
    private String name;

    public ProductName(String name) {
        this.name = name;
    }

    protected ProductName() {

    }

    public String getValue() {
        return name;
    }
}

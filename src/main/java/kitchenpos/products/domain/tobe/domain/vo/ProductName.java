package kitchenpos.products.domain.tobe.domain.vo;

import kitchenpos.products.domain.tobe.domain.policy.ProductNamingRule;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductName {

    @Column(name="name")
    private String name;

    public ProductName(String name, ProductNamingRule rule) {
        rule.checkRule(name);
        this.name = name;
    }

    protected ProductName() {

    }

    public String getValue() {
        return name;
    }
}

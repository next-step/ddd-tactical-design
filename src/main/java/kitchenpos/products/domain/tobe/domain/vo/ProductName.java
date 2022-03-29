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

    public ProductName(String name, ProductNamingRule rule) {
        if (Objects.isNull(rule)) {
            throw new ProductNamingRuleViolationException("상품이름 정책을 선택해 주십시");
        }
        rule.checkRule(name);
        this.name = name;
    }

    protected ProductName() {

    }

    public String getValue() {
        return name;
    }
}

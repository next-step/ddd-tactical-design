package kitchenpos.products.tobe.domain;

import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;

@Embeddable
public class ProductName {
    private String value;

    protected ProductName() { }

    public ProductName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("이름은 비어있을 수 없습니다");
        }
        this.value = name;
    }

    public String value() {
        return value;
    }
}
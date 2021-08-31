package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {
    @Column(name = "name", nullable = false)
    private String name;

    protected ProductName() {}

    ProductName(final String name) {
        validate(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void validate(final String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("상품 이름은 필수값입니다.");
        }
        // TODO:
        // throw new IllegalArgumentException("상품 이름은 비속어가 포함될 수 없습니다.");
    }
}

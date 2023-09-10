package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {
    @Column(name = "name", nullable = false)
    private String name;

    protected ProductName() {
    }

    public ProductName(String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("상품의 이름은 비어 있을 수 없습니다.");
        }
        this.name = name;
    }
}

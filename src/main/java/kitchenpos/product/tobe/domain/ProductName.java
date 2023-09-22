package kitchenpos.product.tobe.domain;

import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class ProductName {

    private String value;

    protected ProductName() {
    }

    public ProductName(String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new IllegalArgumentException("상품 이름은 null 또는 공백을 허용하지 않습니다.");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductName)) {
            return false;
        }
        ProductName that = (ProductName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

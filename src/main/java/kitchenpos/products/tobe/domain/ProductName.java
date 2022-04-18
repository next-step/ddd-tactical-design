package kitchenpos.products.tobe.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.util.StringUtils;

@Embeddable
public class ProductName {

    @Column(name = "name", nullable = false)
    private String value;

    protected ProductName() { }

    public ProductName(String value, PurgomalumClient purgomalumClient) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException("이름은 빈 값이 될 수 없습니다.");
        }

        if (purgomalumClient.containsProfanity(value)) {
            throw new IllegalArgumentException("이름에 비속어가 포함되어 있습니다.");
        }

        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductName that = (ProductName) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public String value() {
        return value;
    }
}

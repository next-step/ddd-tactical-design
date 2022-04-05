package kitchenpos.products.domain.tobe;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {
    private static final String INVALID_NAME_INPUT = "올바른 이름을 입력해야합니다.";

    @Column(name = "name", nullable = false)
    private String name;

    protected ProductName() {
    }

    public ProductName(String name, BanWordFilter banWordFilter) {
        validate(name, banWordFilter);
        this.name = name;
    }

    private void validate(String name, BanWordFilter banWordFilter) {
        if (Objects.isNull(name) || banWordFilter.containsProfanity(name)) {
            throw new IllegalArgumentException(INVALID_NAME_INPUT);
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductName)) return false;
        ProductName that = (ProductName) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}

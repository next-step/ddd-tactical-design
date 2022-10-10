package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {
    private String name;

    protected ProductName() {
    }

    public ProductName(String name) {
        this.name = name;
    }

    public ProductName(String name, PurgomalumClient client) {
        if (Objects.isNull(name) || client.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductName that = (ProductName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

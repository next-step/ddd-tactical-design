package kitchenpos.products.tobe.domain.vo;

import java.util.Objects;
import javax.persistence.Embeddable;
import kitchenpos.global.vo.Name;
import kitchenpos.global.vo.ValueObject;

@Embeddable
public class ProductName extends ValueObject {

    private Name name;

    protected ProductName() {
    }

    public ProductName(Name name) {
        this.name = name;
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
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

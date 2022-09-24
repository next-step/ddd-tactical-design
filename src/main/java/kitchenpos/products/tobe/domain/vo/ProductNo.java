package kitchenpos.products.tobe.domain.vo;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductNo implements Serializable {

    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID value;

    public ProductNo() {
        value = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductNo productNo = (ProductNo) o;
        return Objects.equals(value, productNo.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

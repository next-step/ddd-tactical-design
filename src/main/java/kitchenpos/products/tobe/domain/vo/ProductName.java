package kitchenpos.products.tobe.domain.vo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.exception.EmptyProductNameException;
import kitchenpos.products.tobe.domain.exception.ProfanityProductNameException;

@Embeddable
public class ProductName implements Serializable {

    @Column(name = "name", nullable = false)
    private String value;

    protected ProductName() {}

    public ProductName(String value, PurgomalumClient profanity) {
        validate(value, profanity);
        this.value = value;
    }

    private void validate(String name, PurgomalumClient profanity) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new EmptyProductNameException();
        }

        if (profanity.containsProfanity(name)) {
            throw new ProfanityProductNameException();
        }
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
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

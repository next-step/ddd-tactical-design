package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.exception.ContainProfanityException;
import kitchenpos.products.tobe.domain.exception.ProductNameNullException;
import kitchenpos.support.domain.Value;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName extends Value {

    private String name;

    protected ProductName() {/*no-op*/}

    public ProductName(String name, Profanities profanities) {
        validate(name, profanities);
        this.name = name;
    }

    private void validate(String name, Profanities profanities) {
        if (Objects.isNull(name) || name.trim().isEmpty()) {
            throw new ProductNameNullException();
        }

        if (profanities.containsProfanity(name)) {
            throw new ContainProfanityException();
        }
    }

}

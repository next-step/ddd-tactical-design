package kitchenpos.core.products.tobe.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import kitchenpos.core.products.tobe.domain.exception.InvalidProductNameException;
import kitchenpos.core.products.tobe.domain.support.ProductNameValidationResult;
import kitchenpos.core.shared.domain.ProfanityChecker;
import kitchenpos.core.shared.domain.ValueObject;
import org.jetbrains.annotations.NotNull;

@Embeddable
public class ProductName extends ValueObject<ProductName> {

    private String name;

    @SuppressWarnings("unused")
    protected ProductName() {}

    private ProductName(String name) {
        this.name = name;
    }

    public static ProductName create(@NotNull ProductNamePolicy policy, String name) {
        ProductNameValidationResult result = policy.validate(name);
        if (!result.valid()) {
            throw new InvalidProductNameException((String.join("; ", result.errorMessages())));
        }
        return new ProductName(name.strip());
    }

    public String getName() {
        return name;
    }

    @Override
    @Transient
    protected Object[] getEqualityFields() {
        return new Object[] { name };
    }
}
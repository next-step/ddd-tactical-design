package kitchenpos.product.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProductName {
    private final static ProductNameValidator DEFAULT_PRODUCT_NAME_VALIDATOR = new DefaultProductNameValidator();

    @Column(name = "name", nullable = false)
    private final String name;

    private ProductName(final String name) {
        this.name = name;
    }

    protected ProductName() {
        // 이 메소드를 직접 호출하여 사용하지 말 것
        this.name = "";
    }

    public static ProductName of(final String name, final ProductNameValidator ... additionalProductNameValidators) {
        DEFAULT_PRODUCT_NAME_VALIDATOR.validate(name);
        for (ProductNameValidator productNameValidator : additionalProductNameValidators) {
            productNameValidator.validate(name);
        }
        return new ProductName(name);
    }

    public String value() {
        return name;
    }
}

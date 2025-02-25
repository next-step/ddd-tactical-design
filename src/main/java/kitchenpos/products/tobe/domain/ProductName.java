package kitchenpos.products.tobe.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import kitchenpos.products.tobe.domain.exception.InvalidProductNameException;
import kitchenpos.shared.domain.ProfanityChecker;
import kitchenpos.shared.domain.ValueObject;

@Embeddable
public class ProductName extends ValueObject<ProductName> {

    private String name;

    @SuppressWarnings("unused")
    protected ProductName() {}

    // 외부에서 직접 호출할 수 없는 private 생성자: 검증된 이름만 받음
    private ProductName(String name) {
        this.name = name;
    }

    public static ProductName create(ProfanityChecker profanityChecker, String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidProductNameException("Product name 은 null 이거나 빈 값이 될 수 없습니다.");
        }

        if (profanityChecker.containsProfanity(name)) {
            throw new InvalidProductNameException("Product name 에 비속어가 포함될 수 없습니다.");
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
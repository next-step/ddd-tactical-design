package kitchenpos.products.application;

import kitchenpos.core.products.tobe.domain.ProductNamePolicy;
import kitchenpos.core.products.tobe.domain.support.ProductNameValidationResult;
import kitchenpos.core.shared.domain.ProfanityChecker;

import java.util.List;

public class FakeProductNamePolicy implements ProductNamePolicy {

    private final ProfanityChecker profanityChecker = new FakeProfanityChecker();

    @Override
    public ProductNameValidationResult validate(String name) {
        if (profanityChecker.containsProfanity(name)) {
            return new ProductNameValidationResult(false, List.of("비속어가 포함되어 있습니다."));
        }
        return new ProductNameValidationResult(true, List.of());
    }
}

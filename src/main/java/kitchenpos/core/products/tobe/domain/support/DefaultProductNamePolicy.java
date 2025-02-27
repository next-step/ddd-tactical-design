package kitchenpos.core.products.tobe.domain.support;

import kitchenpos.core.products.tobe.domain.ProductNamePolicy;
import kitchenpos.core.shared.domain.ProfanityChecker;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultProductNamePolicy implements ProductNamePolicy {

    private final ProfanityChecker profanityChecker;

    public DefaultProductNamePolicy(ProfanityChecker profanityChecker) {
        this.profanityChecker = profanityChecker;
    }

    @Override
    public ProductNameValidationResult validate(String name) {
        List<String> errors = new ArrayList<>();
        if (name == null || name.isBlank()) {
            errors.add("Product name은 null 이거나 빈 값이 될 수 없습니다.");
        }
        if (name != null && profanityChecker.containsProfanity(name)) {
            errors.add("Product name에 비속어가 포함될 수 없습니다.");
        }
        return new ProductNameValidationResult(errors.isEmpty(), errors);
    }
}
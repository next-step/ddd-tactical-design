package kitchenpos.product.application.service.validator;

import kitchenpos.product.domain.exception.ProductNameValidationException;
import kitchenpos.product.domain.model.ProfanityFilteringProductNameValidator;
import kitchenpos.shared.port.out.PurgomalumClient;
import org.springframework.stereotype.Component;

@Component
public class DefaultProfanityFilteringProductNameValidator implements ProfanityFilteringProductNameValidator {
    private final PurgomalumClient purgomalumClient;

    public DefaultProfanityFilteringProductNameValidator(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    @Override
    public void validate(String name) {
        if (purgomalumClient.containsProfanity(name)) {
            throw new ProductNameValidationException("상품명에 비속어가 포함되어 있습니다.");
        }
    }
}

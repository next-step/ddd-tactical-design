package kitchenpos.tobe.product.domain;

import kitchenpos.tobe.product.domain.exception.InvalidProductNameException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ProductNameValidator {

    private final PurgomalumAgent purgomalumAgent;

    public ProductNameValidator(PurgomalumAgent purgomalumAgent) {
        this.purgomalumAgent = purgomalumAgent;
    }

    public void validate(final String name) {
        if (!StringUtils.hasText(name)) {
            throw new InvalidProductNameException("상품명은 필수입니다.");
        }
        if (purgomalumAgent.containsProfanity(name)) {
            throw new InvalidProductNameException("상품명은 비속어를 포함하지 않아야 합니다.");
        }
    }

}

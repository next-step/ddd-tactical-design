package kitchenpos.products.tobe.domain.service;

import kitchenpos.products.tobe.domain.interfaces.PurgomalumClient;
import org.springframework.stereotype.Component;

@Component
public class ProductDomainService {

    private final PurgomalumClient purgomalumClient;

    public ProductDomainService(final PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public void validateDisplayedName(final String displayedName) {
        if (displayedName == null) {
            throw new IllegalArgumentException("DisplayedName는 Null이 될 수 없습니다.");
        }

        if (purgomalumClient.containsProfanity(displayedName)) {
            throw new IllegalArgumentException("DisplayedName에는 비속어가 포함 될 수 없습니다.");
        }
    }
}

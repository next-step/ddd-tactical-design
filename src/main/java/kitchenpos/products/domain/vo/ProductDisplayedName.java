package kitchenpos.products.domain.vo;

import kitchenpos.products.domain.exception.InvalidProductDisplayedNameException;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.support.ValueObject;

import java.util.Objects;

public class ProductDisplayedName extends ValueObject {
    final String displayedName;

    public ProductDisplayedName(String displayedName, PurgomalumClient purgomalumClient) {
        if (Objects.isNull(displayedName) || purgomalumClient.containsProfanity(displayedName)) {
            throw new InvalidProductDisplayedNameException();
        }
        this.displayedName = displayedName;
    }

    public String getDisplayedName() {
        return displayedName;
    }
}

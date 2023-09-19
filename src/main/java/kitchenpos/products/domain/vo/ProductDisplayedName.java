package kitchenpos.products.domain.vo;

import kitchenpos.products.domain.exception.InvalidProductDisplayedNameException;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.support.ValueObject;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductDisplayedName extends ValueObject {
    String displayedName;

    public ProductDisplayedName(String displayedName, PurgomalumClient purgomalumClient) {
        if (Objects.isNull(displayedName) || purgomalumClient.containsProfanity(displayedName)) {
            throw new InvalidProductDisplayedNameException();
        }
        this.displayedName = displayedName;
    }

    public ProductDisplayedName() {

    }

    public String getDisplayedName() {
        return displayedName;
    }
}

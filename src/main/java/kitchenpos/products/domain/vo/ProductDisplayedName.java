package kitchenpos.products.domain.vo;

import kitchenpos.products.infra.DefaultPurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.domain.exception.InvalidProductDisplayedNameException;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.util.Objects;

public class ProductDisplayedName {
    final String displayedName;
    PurgomalumClient purgomalumClient = new DefaultPurgomalumClient(new RestTemplateBuilder());

    public ProductDisplayedName(String displayedName) {
        if (Objects.isNull(displayedName) || purgomalumClient.containsProfanity(displayedName)) {
            throw new InvalidProductDisplayedNameException();
        }
        this.displayedName = displayedName;
    }

    public String getDisplayedName() {
        return displayedName;
    }
}

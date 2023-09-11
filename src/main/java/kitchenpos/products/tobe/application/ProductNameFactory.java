package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.PurgomalumClient;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ProductNameFactory {

    private final PurgomalumClient purgomalumClient;

    public ProductNameFactory(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public ProductName createProductName(String name) {
        if (!StringUtils.hasText(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("이름은 비어있을 수 없거나 비속어를 포함하면 안됩니다.");
        }
        return new ProductName(name);
    }
}
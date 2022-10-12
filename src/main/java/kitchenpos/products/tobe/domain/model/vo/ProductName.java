package kitchenpos.products.tobe.domain.model.vo;

import kitchenpos.global.domain.client.ProfanityCheckClient;
import kitchenpos.global.domain.vo.ValueObject;
import org.springframework.util.Assert;

public class ProductName extends ValueObject {

    private final String name;

    public ProductName(String name, ProfanityCheckClient profanityCheckClient) {
        Assert.isTrue(
            !profanityCheckClient.containsProfanity(name),
            String.format("상품 이름에는 비속어가 포함될 수 없습니다. [name: %s]", name)
        );
        this.name = name;
    }
}

package kitchenpos.products.tobe.domain.model.vo;

import java.util.Objects;
import kitchenpos.global.domain.ProfanityCheckClient;
import org.springframework.util.Assert;

public class ProductName {

    private final String name;

    public ProductName(String name, ProfanityCheckClient profanityCheckClient) {
        Assert.isTrue(
            !profanityCheckClient.containsProfanity(name),
            String.format("상품 이름에는 비속어가 포함될 수 없습니다. [name: %s]", name)
        );
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductName that = (ProductName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

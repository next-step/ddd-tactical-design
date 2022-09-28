package kitchenpos.products.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.products.exception.InvalidProductNameException;

@Embeddable
public class ProductName {

    @Column(name = "name", nullable = false)
    private String value;

    protected ProductName() {
    }

    public ProductName(String value, ProductProfanityCheckClient productProfanityCheckClient) {
        this.value = value;
        validateNull(this.value);
        validateProfanity(this.value, productProfanityCheckClient);
    }

    private void validateNull(String value) {
        if (Objects.isNull(value)) {
            throw new InvalidProductNameException("올바르지 않은 상품 이름입니다.");
        }
    }

    private void validateProfanity(String value, ProductProfanityCheckClient productProfanityCheckClient) {
        if (productProfanityCheckClient.containsProfanity(value)) {
            throw new InvalidProductNameException("상품 이름에는 비속어가 포함될 수 없습니다.");
        }
    }

    public String getValue() {
        return value;
    }
}

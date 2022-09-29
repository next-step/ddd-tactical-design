package kitchenpos.products.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.products.exception.InvalidProductNameException;
import kitchenpos.profanity.infra.ProfanityCheckClient;

@Embeddable
public class ProductName {

    @Column(name = "name", nullable = false)
    private String value;

    protected ProductName() {
    }

    public ProductName(String value, ProfanityCheckClient profanityCheckClient) {
        this.value = value;
        validateNull(this.value);
        validateProfanity(this.value, profanityCheckClient);
    }

    private void validateNull(String value) {
        if (Objects.isNull(value)) {
            throw new InvalidProductNameException("올바르지 않은 상품 이름입니다.");
        }
    }

    private void validateProfanity(String value, ProfanityCheckClient profanityCheckClient) {
        if (profanityCheckClient.containsProfanity(value)) {
            throw new InvalidProductNameException("상품 이름에는 비속어가 포함될 수 없습니다.");
        }
    }

    public String getValue() {
        return value;
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
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

package kitchenpos.products.tobe.domain.model.vo;

import kitchenpos.global.document.ValueObject;
import kitchenpos.global.infrastructure.external.BannedWordCheckClient;
import kitchenpos.products.tobe.exception.IllegalProductNameException;

import java.util.Objects;

public final class ProductName implements ValueObject {

    private final String name;

    public ProductName(String name, BannedWordCheckClient bannedWordCheckClient) {
        validate(name, bannedWordCheckClient);
        this.name = name;
    }

    private void validate(String name, BannedWordCheckClient bannedWordCheckClient) {
        if (Objects.isNull(name) || bannedWordCheckClient.containsProfanity(name)) {
            throw new IllegalProductNameException();
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductName that = (ProductName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

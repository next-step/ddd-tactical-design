package kitchenpos.products.tobe.domain;

import java.util.Objects;

public class ToBeDisplayedName {
    private final String productName;

    public ToBeDisplayedName(final String productName) {
        if (Objects.isNull(productName) || productName.isBlank()) {
            throw new IllegalArgumentException("상품명은 필수로 입력해야 합니다.");
        }
        this.productName = productName;
    }

    public ToBeDisplayedName(final String displayedName, final ProfanityName profanityName) {
        this(displayedName);
        if (profanityName.contains(productName)) {
            throw new IllegalArgumentException("상품명에 비속어가 포함되어 있습니다.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ToBeDisplayedName that = (ToBeDisplayedName) o;
        return Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productName);
    }
}

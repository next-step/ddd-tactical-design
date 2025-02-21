package kitchenpos.products.tobe.domain;

import java.util.Objects;

public class DisplayedName {
    private final String productName;

    public DisplayedName(final String productName) {
        if (Objects.isNull(productName) || productName.isBlank()) {
            throw new IllegalArgumentException("상품명은 필수로 입력해야 합니다.");
        }
        this.productName = productName;
    }

    public DisplayedName(final String displayedName, final ProfanityName profanityName) {
        this(displayedName);
        if (profanityName.contains(productName)) {
            throw new IllegalArgumentException("상품명에 비속어가 포함되어 있습니다.");
        }
    }

    public String getDisplayedName() {
        return productName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DisplayedName that = (DisplayedName) o;
        return Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productName);
    }
}

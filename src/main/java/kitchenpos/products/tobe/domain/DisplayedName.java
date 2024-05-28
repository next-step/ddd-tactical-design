package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class DisplayedName {

    @Column(name = "name", columnDefinition = "varchar(255)", nullable = false)
    private String value;

    protected DisplayedName() {
    }

    DisplayedName(final String value, final ProfanityCheckProvider profanityCheckProvider) {
        if (value == null || value.isEmpty()) {
            throw new DisplayedNameEmptyException("상품명은 필수값입니다.");
        }
        if (profanityCheckProvider.containsProfanity(value)) {
            throw new DisplayedNameProfanityIncludedException("이름에 적절하지 않은 단어가 포함되어있습니다.");
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DisplayedName that = (DisplayedName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

package kitchenpos.products.tobe.domain.vo;

import kitchenpos.products.tobe.domain.Profanity;
import kitchenpos.products.tobe.domain.vo.exception.InvalidProductNameException;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class DisplayedName {

    @Column(name = "name", nullable = false)
    private String value;

    protected DisplayedName() {
    }

    private DisplayedName(final String value) {
        this.value = value;
    }

    public static DisplayedName valueOf(final String value, final Profanity profanity) {
        if (isNullOrEmpty(value) || profanity.containsProfanity(value)) {
            throw new InvalidProductNameException();
        }
        return new DisplayedName(value);
    }

    private static boolean isNullOrEmpty(final String value) {
        return !StringUtils.hasText(value);
    }

    public String value() {
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
        DisplayedName that = (DisplayedName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

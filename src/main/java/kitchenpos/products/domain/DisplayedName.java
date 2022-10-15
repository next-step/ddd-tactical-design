package kitchenpos.products.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.springframework.util.ObjectUtils;

@Embeddable
public class DisplayedName {
    @Column(name = "displayedName", nullable = false)
    private String displayedName;

    protected DisplayedName() {
    }

    private DisplayedName(String displayedName, ProfanityValidator profanity) {
        validate(displayedName, profanity);
        this.displayedName = displayedName;
    }

    public static DisplayedName of(String displayedName, ProfanityValidator profanity) {
        return new DisplayedName(displayedName, profanity);
    }

    private void validate(String displayedName, ProfanityValidator profanity) {
        if (ObjectUtils.isEmpty(displayedName)) {
            throw new IllegalArgumentException("이름은 비어 있을 수 없습니다.");
        }

        if (Objects.isNull(profanity)) {
            throw new IllegalArgumentException("비속어 검증이 비어 있을 수 없습니다.");
        }

        if (profanity.isHas(displayedName)) {
            throw new IllegalArgumentException("이름에 비속어가 포함되어 있습니다.");
        }
    }

    public String value() {
        return displayedName;
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
        return Objects.equals(value(), that.value());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value());
    }
}

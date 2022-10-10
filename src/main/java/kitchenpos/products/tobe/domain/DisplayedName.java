package kitchenpos.products.tobe.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.products.domain.Profanity;

@Embeddable
public class DisplayedName {
    @Column(name = "displayedName", nullable = false)
    private String displayedName;

    protected DisplayedName() {}

    public DisplayedName(String displayedName, Profanity profanity) {
        validate(displayedName, profanity);
        this.displayedName = displayedName;
    }

    private void validate(String displayedName, Profanity profanity) {
        if (Objects.isNull(displayedName)) {
            throw new IllegalArgumentException("이름은 비어 있을 수 없습니다.");
        }

        if (Objects.isNull(profanity)) {
            throw new IllegalArgumentException("비속어가 비어 있을 수 없습니다.");
        }

        if (profanity.isHas(displayedName)) {
            throw new IllegalArgumentException("이름에 비속어가 포함되어 있습니다.");
        }
    }

    public String getDisplayedName() {
        return displayedName;
    }
}

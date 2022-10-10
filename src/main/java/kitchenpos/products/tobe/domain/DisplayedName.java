package kitchenpos.products.tobe.domain;

import java.util.Objects;
import javax.persistence.Embeddable;
import kitchenpos.products.domain.Profanity;

@Embeddable
public class DisplayedName {
    private String name;

    protected DisplayedName() {}

    public DisplayedName(String name, Profanity profanity) {
        validate(name, profanity);
        this.name = name;
    }

    private void validate(String name, Profanity profanity) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("이름은 비어 있을 수 없습니다.");
        }

        if (Objects.isNull(profanity)) {
            throw new IllegalArgumentException("비속어가 비어 있을 수 없습니다.");
        }

        if (profanity.isHas(name)) {
            throw new IllegalArgumentException("이름에 비속어가 포함되어 있습니다.");
        }
    }

    public String getName() {
        return name;
    }
}

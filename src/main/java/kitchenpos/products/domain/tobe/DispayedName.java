package kitchenpos.products.domain.tobe;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class DispayedName {

    private String name;

    protected DispayedName() {
    }

    public DispayedName(final String name, final Profanities profanities) {
        if (profanities.contains(name)) throw new IllegalArgumentException("비속어가 포함되어 있습니다.");

        this.name = name;
    }
}


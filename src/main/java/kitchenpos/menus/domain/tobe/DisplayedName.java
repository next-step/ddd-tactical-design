package kitchenpos.menus.domain.tobe;

import jakarta.persistence.Embeddable;
import kitchenpos.products.infra.tobe.Profanities;

import java.util.Optional;

@Embeddable
public class DisplayedName {

    private String name;

    protected DisplayedName() {
    }

    private DisplayedName(final String name) {
        this.name = name;
    }

    public static final DisplayedName createDisplayedName(final String name, final Profanities profanities) {
        if (Optional.ofNullable(name).isEmpty()){
            throw new IllegalArgumentException("이름을 반드시 입력해주세요.");
        }

        if (profanities.contains(name)) {
            throw new IllegalArgumentException("비속어가 포함되어 있습니다.");
        }

        return new DisplayedName(name);
    }

    public String getName() {
        return name;
    }
}


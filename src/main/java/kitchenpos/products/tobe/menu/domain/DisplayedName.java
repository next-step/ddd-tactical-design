package kitchenpos.products.tobe.menu.domain;

import kitchenpos.products.tobe.support.Value;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DisplayedName extends Value<DisplayedName> {

    @Column(name = "price", nullable = false)
    private String name;

    protected DisplayedName() {
    }

    public DisplayedName(String name, Profanities profanities) {
        if (profanities.contains(name)) {
            throw new IllegalArgumentException("이름에는 비속어가 포함될 수 없습니다.");
        }
        this.name = name;
    }

}


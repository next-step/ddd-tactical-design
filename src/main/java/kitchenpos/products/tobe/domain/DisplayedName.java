package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.util.StringUtils;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class DisplayedName {

    private String name;

    protected DisplayedName() {
    }

    public DisplayedName(String name, Profanity profanity) {
        if(StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("이름이 비어 있습니다.");
        }

        if(profanity.containsProfanity(name)) {
            throw new IllegalArgumentException("상품의 이름에는 비속어가 포함될 수 없습니다.");
        }

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DisplayedName that = (DisplayedName) o;

        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}

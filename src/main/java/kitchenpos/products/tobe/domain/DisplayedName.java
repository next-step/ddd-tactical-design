package kitchenpos.products.tobe.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class DisplayedName {
    private String name;

    public DisplayedName() {
    }

    public DisplayedName(String name) {
        this.name = name;
    }

    public static DisplayedName of(String name, PurgomalumClient purgomalumClient) {
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("상품 명에는 비속어가 포함 될 수 없습니다.");
        }
        return new DisplayedName(name);
    }

    @Override
    public String toString() {
        return name;
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
        return Objects.hash(name);
    }
}

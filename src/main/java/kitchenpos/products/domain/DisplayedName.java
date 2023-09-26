package kitchenpos.products.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class DisplayedName {
    private String name;

    protected DisplayedName() {
    }

    public DisplayedName(String name, PurgomalumClient purgomalumClient) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException(String.format("이름은 없거나 빈 값일 수 없습니다. 현재 값: %s", name));
        }
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException(String.format("이름은 욕설을 포함할 수 없습니다. 현재 값: %s", name));
        }

        this.name = name;
    }

    public String stringValue() {
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

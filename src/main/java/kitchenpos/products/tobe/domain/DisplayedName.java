package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class DisplayedName {

    @Column(name = "name", nullable = false)
    private String name;

    public DisplayedName() {
    }

    private DisplayedName(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("이름은 필수 항목입니다.");
        }
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DisplayedName name1 = (DisplayedName) o;
        return Objects.equals(name, name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "DisplayedName{" +
                "name='" + name + '\'' +
                '}';
    }

    public static DisplayedName create(String name) {
        return new DisplayedName(name);
    }
}

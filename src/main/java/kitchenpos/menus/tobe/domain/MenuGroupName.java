package kitchenpos.menus.tobe.domain;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MenuGroupName {

    @Column(name = "name", nullable = false)
    private String name;

    public MenuGroupName(String name) {
        if (isEmpty(name)) {
            throw new IllegalArgumentException("MenuGroup명은 필수입니다");
        }

        this.name = name;
    }

    protected MenuGroupName() {
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuGroupName)) {
            return false;
        }
        MenuGroupName that = (MenuGroupName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

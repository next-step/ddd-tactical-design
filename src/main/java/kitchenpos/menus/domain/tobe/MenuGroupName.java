package kitchenpos.menus.domain.tobe;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuGroupName {

    @Column(name = "name", nullable = false)
    private String name;

    protected MenuGroupName() {
    }

    public MenuGroupName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.name = name;
    }
}

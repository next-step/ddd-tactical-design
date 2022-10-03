package kitchenpos.menus.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuName {

    @Column(name = "name", nullable = false)
    private String name;

    protected MenuName() {
    }

    public MenuName(String name) {
        validateMenuName(name);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    private void validateMenuName(String name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException();
        }
    }
}

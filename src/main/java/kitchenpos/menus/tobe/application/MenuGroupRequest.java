package kitchenpos.menus.tobe.application;

import java.util.Objects;

public class MenuGroupRequest {
    private String name;

    public MenuGroupRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void validateName() {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}

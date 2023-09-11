package kitchenpos.menus.tobe.domain;

import java.util.Objects;

public class MenuGroupName {

    private String name;

    public MenuGroupName(String name) {
        checkMenuName(name);
        this.name = name;
    }

    public void checkMenuName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    public String getMenuName() {
        return this.name;
    }


}

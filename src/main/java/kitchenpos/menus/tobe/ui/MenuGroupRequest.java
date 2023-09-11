package kitchenpos.menus.tobe.ui;

import javax.validation.constraints.NotBlank;

public class MenuGroupRequest {

    @NotBlank
    private final String name;

    public MenuGroupRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

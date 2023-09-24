package kitchenpos.menus.application.menugroup.dto;

public class MenuGroupRequest {
    private final String name;

    public MenuGroupRequest(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

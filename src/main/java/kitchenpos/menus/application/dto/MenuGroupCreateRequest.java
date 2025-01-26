package kitchenpos.menus.application.dto;

public class MenuGroupCreateRequest {
    private final String name;

    public MenuGroupCreateRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

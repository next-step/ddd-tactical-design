package kitchenpos.menus.application.dto;

public class MenuGroupCreateRequest {
    private String name;

    public MenuGroupCreateRequest() {
    }

    private MenuGroupCreateRequest(String name) {
        this.name = name;
    }

    public static MenuGroupCreateRequest create(String name) {
        return new MenuGroupCreateRequest(name);
    }

    public String getName() {
        return name;
    }
}

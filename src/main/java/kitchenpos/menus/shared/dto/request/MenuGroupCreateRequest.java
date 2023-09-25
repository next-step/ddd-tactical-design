package kitchenpos.menus.shared.dto.request;

public class MenuGroupCreateRequest {
    private String name;

    public String getName() {
        return name;
    }

    public MenuGroupCreateRequest(String name) {
        this.name = name;
    }
}

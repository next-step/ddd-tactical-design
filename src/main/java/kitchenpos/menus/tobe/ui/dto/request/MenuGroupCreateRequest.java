package kitchenpos.menus.tobe.ui.dto.request;

public class MenuGroupCreateRequest {
    private String Name;

    public MenuGroupCreateRequest(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }
}

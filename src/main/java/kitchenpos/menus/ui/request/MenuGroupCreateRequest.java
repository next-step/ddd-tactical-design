package kitchenpos.menus.ui.request;

public class MenuGroupCreateRequest {

    private String name;

    protected MenuGroupCreateRequest() {
    }

    public MenuGroupCreateRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

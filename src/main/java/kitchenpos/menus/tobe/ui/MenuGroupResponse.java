package kitchenpos.menus.tobe.ui;

public class MenuGroupResponse {

    private final String id;

    private final String name;

    public MenuGroupResponse(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

package kitchenpos.menus.model;

public class MenuGroupRequest {
    private String name;

    public MenuGroupRequest(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

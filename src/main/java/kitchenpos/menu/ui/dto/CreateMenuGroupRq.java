package kitchenpos.menu.ui.dto;

public class CreateMenuGroupRq {
    private String name;

    public CreateMenuGroupRq(String name) {
        this.name = name;
    }

    public CreateMenuGroupRq(CreateMenuGroupRq menuGroup) {
        this.name = menuGroup.getName();
    }

    public CreateMenuGroupRq() {
    }

    public String getName() {
        return name;
    }
}

package kitchenpos.menus.dto;

public class CreateMenuGroupRequest {

    private final String name;

    public CreateMenuGroupRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

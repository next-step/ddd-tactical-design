package kitchenpos.menu.application.service.model;

public class CreateMenuGroupRequest {
    private String name;

    public CreateMenuGroupRequest() {
    }

    public CreateMenuGroupRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

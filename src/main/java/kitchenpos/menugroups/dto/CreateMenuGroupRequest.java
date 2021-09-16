package kitchenpos.menugroups.dto;

public class CreateMenuGroupRequest {
    private String name;

    protected CreateMenuGroupRequest() {}

    public CreateMenuGroupRequest(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

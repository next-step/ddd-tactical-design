package kitchenpos.menu.application.dto;

public class CreateMenuGroupServiceRq {
    private String name;

    public CreateMenuGroupServiceRq(String name) {
        this.name = name;
    }

    public CreateMenuGroupServiceRq() {
    }

    public String getName() {
        return name;
    }
}

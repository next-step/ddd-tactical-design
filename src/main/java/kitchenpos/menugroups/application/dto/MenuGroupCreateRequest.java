package kitchenpos.menugroups.application.dto;

public class MenuGroupCreateRequest {
    final private String name;

    public MenuGroupCreateRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

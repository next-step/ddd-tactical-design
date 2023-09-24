package kitchenpos.menugroups.ui.dto.request;

public class MenuGroupCreateRestRequest {
    final private String name;

    public MenuGroupCreateRestRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}

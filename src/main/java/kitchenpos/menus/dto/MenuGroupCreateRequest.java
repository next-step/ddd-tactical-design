package kitchenpos.menus.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class MenuGroupCreateRequest {
    @JsonProperty("name")
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public MenuGroupCreateRequest(@JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

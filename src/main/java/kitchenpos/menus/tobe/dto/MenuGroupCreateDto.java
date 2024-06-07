package kitchenpos.menus.tobe.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class MenuGroupCreateDto {
    private String name;

    @JsonCreator
    public String getName() {
        return name;
    }

    public MenuGroupCreateDto(String name) {
        this.name = name;
    }
}

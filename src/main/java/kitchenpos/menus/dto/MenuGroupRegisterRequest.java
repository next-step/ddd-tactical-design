package kitchenpos.menus.dto;

import kitchenpos.support.dto.DTO;

public class MenuGroupRegisterRequest extends DTO {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MenuGroupRegisterRequest() {
    }

    public MenuGroupRegisterRequest(String name) {
        this.name = name;
    }
}

package kitchenpos.menus.dto;

import kitchenpos.support.dto.DTO;

import javax.validation.constraints.NotBlank;

public class MenuGroupRegisterRequest extends DTO {
    @NotBlank(message = "메뉴그룹의_이름은_필수값_입니다")
    private String name;

    public MenuGroupRegisterRequest() {
    }

    public MenuGroupRegisterRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

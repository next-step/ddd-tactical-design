package kitchenpos.menus.dto;

import kitchenpos.menus.domain.tobe.domain.TobeMenuGroup;
import kitchenpos.support.dto.DTO;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class MenuGroupDto extends DTO {
    private UUID id;
    private String name;

    public MenuGroupDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public MenuGroupDto(TobeMenuGroup menuGroup) {
        this.id = menuGroup.getId().getValue();
        this.name = menuGroup.getName().getValue();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

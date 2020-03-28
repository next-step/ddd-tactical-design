package kitchenpos.menus.tobe.domain.menugroup.dto;

import kitchenpos.menus.tobe.domain.menugroup.entity.MenuGroup;

public class MenuGroupDto {
    private Long id;
    private String name;

    public MenuGroupDto (MenuGroup menuGroup){
        this.id = menuGroup.getId();
        this.name = menuGroup.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

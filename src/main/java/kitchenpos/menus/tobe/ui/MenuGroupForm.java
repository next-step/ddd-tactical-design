package kitchenpos.menus.tobe.ui;

import kitchenpos.menus.tobe.domain.TobeMenuGroup;

import java.util.UUID;

public class MenuGroupForm {
    public UUID id;
    public String name;

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

    public static MenuGroupForm of(TobeMenuGroup saveMenuGroup) {
        MenuGroupForm form = new MenuGroupForm();
        form.setId(saveMenuGroup.getId());
        form.setName(saveMenuGroup.getName());
        return form;
    }
}

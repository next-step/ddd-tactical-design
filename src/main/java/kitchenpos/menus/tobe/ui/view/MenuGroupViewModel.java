package kitchenpos.menus.tobe.ui.view;

import java.util.UUID;
import kitchenpos.menus.tobe.query.result.MenuQueryResult;
import kitchenpos.menus.tobe.domain.entity.MenuGroup;

public class MenuGroupViewModel {
    private final String id;
    private final String name;

    private MenuGroupViewModel(UUID id, String name) {
        this.id = id.toString();
        this.name = name;
    }

    private MenuGroupViewModel(String id, String name) {
        this.id = id.toString();
        this.name = name;
    }

    public static MenuGroupViewModel from(MenuGroup menuGroup) {
        if (menuGroup == null) {
            return null;
        }
        return new MenuGroupViewModel(menuGroup.getId(), menuGroup.getName());
    }

    public static MenuGroupViewModel from(MenuQueryResult menuQueryResult) {
        return new MenuGroupViewModel(menuQueryResult.getMenuGroupId(), menuQueryResult.getMenuGroupName());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

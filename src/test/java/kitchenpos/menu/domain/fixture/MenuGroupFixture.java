package kitchenpos.menu.domain.fixture;

import java.util.UUID;
import kitchenpos.menu.application.dto.MenuGroupRequest;
import kitchenpos.menu.domain.entity.MenuGroup;
import kitchenpos.menu.domain.model.MenuGroupId;
import kitchenpos.menu.domain.model.MenuGroupName;

public record MenuGroupFixture(UUID id, String 메뉴그룹명) {

    public static final String DEFAULT_MENU_GROUP_NAME = "치킨";

    public static MenuGroupFixture init() {
        return new MenuGroupFixture(UUID.randomUUID(), DEFAULT_MENU_GROUP_NAME);
    }

    public static MenuGroupFixture test(String 메뉴그룹명) {
        return new MenuGroupFixture(
            UUID.randomUUID(),
            메뉴그룹명
        );
    }

    public MenuGroup toEntity() {
        return new MenuGroup(MenuGroupId.of(id), new MenuGroupName(메뉴그룹명));
    }

    public MenuGroupRequest.Create create() {
        return new MenuGroupRequest.Create(메뉴그룹명);
    }
}


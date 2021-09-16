package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.dto.MenuGroupResponse;

import java.util.UUID;

public interface MenuGroupTranslator {
    MenuGroupResponse getMenuGroup(UUID menuGroupId);
}

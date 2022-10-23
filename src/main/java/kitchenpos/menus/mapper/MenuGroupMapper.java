package kitchenpos.menus.mapper;

import kitchenpos.menus.dto.MenuGroupResponse;
import kitchenpos.menus.tobe.domain.entity.MenuGroup;
import org.springframework.stereotype.Component;

@Component
public class MenuGroupMapper {
    public MenuGroupResponse toMenuGroupResponse(MenuGroup menuGroup) {
        if (menuGroup == null) {
            return new MenuGroupResponse();
        }

        return new MenuGroupResponse(
                menuGroup.getId(),
                menuGroup.getName()
        );
    }
}

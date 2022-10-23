package kitchenpos.menus.mapper;

import kitchenpos.menus.dto.MenuResponse;
import kitchenpos.menus.tobe.domain.entity.Menu;
import org.springframework.stereotype.Component;

@Component
public class MenuMapper {
    private final MenuGroupMapper menuGroupMapper;
    private final MenuProductMapper menuProductMapper;

    public MenuMapper(MenuGroupMapper menuGroupMapper, MenuProductMapper menuProductMapper) {
        this.menuGroupMapper = menuGroupMapper;
        this.menuProductMapper = menuProductMapper;
    }

    public MenuResponse toMenuResponse(Menu menu) {
        if (menu == null) {
            return new MenuResponse();
        }

        MenuResponse menuResponse = new MenuResponse(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                menu.isDisplayed(),
                menuGroupMapper.toMenuGroupResponse(menu.getMenuGroup()),
                menuProductMapper.toMenuProductResponseList(menu.getMenuProducts())
        );
        return menuResponse;
    }
}

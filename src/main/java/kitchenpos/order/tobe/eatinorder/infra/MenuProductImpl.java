package kitchenpos.order.tobe.eatinorder.infra;

import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.order.tobe.eatinorder.domain.MenuClient;
import kitchenpos.order.tobe.eatinorder.domain.MenuDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class MenuProductImpl implements MenuClient {
    private final MenuRepository menuRepository;

    public MenuProductImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public List<MenuDto> findMenusByIds(List<UUID> menuIds) {
        List<Menu> menus = menuRepository.findAllByIdIn(menuIds);

        return menus.stream()
                .map(menu -> MenuDto.of(menu.getId(), menu.isDisplayed(), menu.getPrice()))
                .toList();
    }
}

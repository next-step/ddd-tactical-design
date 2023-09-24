package kitchenpos.eatinorders.infra;

import java.util.UUID;

import org.springframework.stereotype.Component;

import kitchenpos.eatinorders.domain.order.MenuApiRepository;
import kitchenpos.eatinorders.domain.order.vo.MenuVo;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;

@Component
public class DefaultMenuApiRepository implements MenuApiRepository {
    private final MenuRepository menuRepository;

    public DefaultMenuApiRepository(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public MenuVo findById(UUID menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(IllegalArgumentException::new);
        return new MenuVo(menu.getId(), menu.getPrice().longValue(), menu.isDisplayed());
    }
}

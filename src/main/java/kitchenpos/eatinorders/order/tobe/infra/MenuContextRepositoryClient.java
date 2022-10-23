package kitchenpos.eatinorders.order.tobe.infra;

import kitchenpos.eatinorders.order.tobe.domain.MenuContextClient;
import kitchenpos.eatinorders.order.tobe.domain.vo.MenuSpecification;
import kitchenpos.menus.menu.tobe.domain.Menu;
import kitchenpos.menus.menu.tobe.domain.MenuRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MenuContextRepositoryClient implements MenuContextClient {

    private final MenuRepository menuRepository;

    public MenuContextRepositoryClient(final MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public MenuSpecification findOrderMenuItemById(final UUID id) {
        final Menu menu = menuRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return new MenuSpecification(menu.id(), menu.price(), menu.isDisplayed());
    }
}

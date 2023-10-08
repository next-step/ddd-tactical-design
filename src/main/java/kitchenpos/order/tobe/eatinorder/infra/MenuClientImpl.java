package kitchenpos.order.tobe.eatinorder.infra;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menu.tobe.domain.Menu;
import kitchenpos.menu.tobe.domain.MenuRepository;
import kitchenpos.order.tobe.eatinorder.application.dto.EatInOrderLintItemDto;
import kitchenpos.order.tobe.eatinorder.domain.MenuClient;
import org.springframework.stereotype.Component;

@Component
public class MenuClientImpl implements MenuClient {

    private final MenuRepository menuRepository;

    public MenuClientImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public List<Menu> getMenusByMenuIds(List<EatInOrderLintItemDto> orderLineItems) {
        List<UUID> menuIds = orderLineItems.stream()
            .map(EatInOrderLintItemDto::getMenuId)
            .collect(Collectors.toList());

        return menuRepository.findAllByIdIn(menuIds);
    }
}

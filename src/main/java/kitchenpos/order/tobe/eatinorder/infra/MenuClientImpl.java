package kitchenpos.order.tobe.eatinorder.infra;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menu.tobe.domain.Menu;
import kitchenpos.menu.tobe.domain.MenuRepository;
import kitchenpos.order.tobe.eatinorder.application.dto.EatInOrderLineItemDto;
import kitchenpos.order.tobe.eatinorder.domain.service.MenuClient;
import kitchenpos.order.tobe.eatinorder.domain.service.MenuDto;
import org.springframework.stereotype.Component;

@Component
public class MenuClientImpl implements MenuClient {

    private final MenuRepository menuRepository;

    public MenuClientImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public List<MenuDto> getMenusByMenuIds(List<EatInOrderLineItemDto> orderLineItems) {
        List<UUID> menuIds = orderLineItems.stream()
            .map(EatInOrderLineItemDto::getEatInOrderMenuId)
            .collect(Collectors.toList());

        return menuRepository.findAllByIdIn(menuIds)
            .stream()
            .map(MenuDto::from)
            .collect(Collectors.toList());
    }
}

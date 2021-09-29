package kitchenpos.eatinorders.tobe.domain.translator;

import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.eatinorders.tobe.domain.model.OrderMenu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MockOrderMenuTranslator implements OrderMenuTranslator {

    private final MenuRepository menuRepository;

    public MockOrderMenuTranslator(final MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public List<OrderMenu> findAllByIdIn(final List<UUID> ids) {
        return menuRepository.findAllByIdIn(ids)
                .stream()
                .map(menu -> new OrderMenu(menu.getId(), new Price(menu.getPrice()), menu.isDisplayed()))
                .collect(Collectors.toList());
    }
}

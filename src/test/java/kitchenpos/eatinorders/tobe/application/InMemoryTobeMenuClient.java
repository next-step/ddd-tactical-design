package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.OrderMenu;
import kitchenpos.eatinorders.tobe.domain.TobeMenuClient;
import kitchenpos.menus.tobe.domain.menu.TobeMenu;
import kitchenpos.menus.tobe.domain.menu.TobeMenuRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryTobeMenuClient implements TobeMenuClient {
    private final TobeMenuRepository tobeMenuRepository;

    public InMemoryTobeMenuClient(final TobeMenuRepository tobeMenuRepository) {
        this.tobeMenuRepository = tobeMenuRepository;
    }

    @Override
    public OrderMenu getOrderMenu(final UUID id) {
        TobeMenu menu = tobeMenuRepository.findById(id).orElseThrow();
        return new OrderMenu(menu.getId(), menu.isDisplayed(), menu.getPriceValue());
    }

    @Override
    public List<OrderMenu> findAllByIdIn(final List<UUID> ids) {
        List<TobeMenu> tobeMenus = tobeMenuRepository.findAllByIdIn(ids);
        return tobeMenus.stream()
                        .map(it -> new OrderMenu(it.getId(), it.isDisplayed(), it.getPriceValue()))
                        .collect(Collectors.toList());
    }

}

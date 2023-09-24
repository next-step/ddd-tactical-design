package kitchenpos.eatinorders.tobe.infra;

import kitchenpos.eatinorders.tobe.domain.OrderMenu;
import kitchenpos.eatinorders.tobe.domain.TobeMenuClient;
import kitchenpos.menus.tobe.domain.menu.TobeMenu;
import kitchenpos.menus.tobe.domain.menu.TobeMenuRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class DefaultTobeMenuClient implements TobeMenuClient {
    private final TobeMenuRepository menuRepository;

    public DefaultTobeMenuClient(final TobeMenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }


    @Override
    public OrderMenu getOrderMenu(final UUID id) {
        TobeMenu tobeMenu = menuRepository.findById(id)
                                          .orElseThrow(() -> new NoSuchElementException("메뉴를 찾을 수 없습니다."));
        return new OrderMenu(id, tobeMenu.isDisplayed(), tobeMenu.getPriceValue());
    }

    @Override
    public List<OrderMenu> findAllByIdIn(final List<UUID> ids) {
        List<TobeMenu> tobeMenus = menuRepository.findAllByIdIn(ids);
        return tobeMenus.stream()
                        .map(it -> new OrderMenu(it.getId(), it.isDisplayed(), it.getPriceValue()))
                        .collect(Collectors.toList());
    }
}

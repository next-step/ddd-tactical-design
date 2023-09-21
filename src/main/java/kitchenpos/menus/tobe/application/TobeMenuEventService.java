package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.menu.MenuPrice;
import kitchenpos.menus.tobe.domain.menu.TobeMenu;
import kitchenpos.menus.tobe.domain.menu.TobeMenuRepository;
import kitchenpos.menus.tobe.domain.menu.TobeProductClient;
import kitchenpos.products.tobe.domain.event.ProductChangedPriceEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Service
public class TobeMenuEventService {
    private final TobeMenuRepository menuRepository;

    public TobeMenuEventService(final TobeMenuRepository menuRepository, final TobeProductClient tobeProductClient) {
        this.menuRepository = menuRepository;
    }

    @TransactionalEventListener(ProductChangedPriceEvent.class)
    @Transactional
    public void updatePrice(ProductChangedPriceEvent event) {
        List<TobeMenu> tobeMenus = menuRepository.findAllByProductId(event.getProductId());

        for (final TobeMenu tobeMenu : tobeMenus) {
            tobeMenu.getTobeMenuProducts()
                    .stream()
                    .filter(it -> it.getProductId().equals(event.getProductId()))
                    .forEach(it -> it.updatePrice(new MenuPrice(event.getChangedPrice())));
        }
    }

}

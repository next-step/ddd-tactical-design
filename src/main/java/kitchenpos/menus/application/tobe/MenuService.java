package kitchenpos.menus.application.tobe;

import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.menus.domain.tobe.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

//@Service
public class MenuService {
    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Transactional
    public void changePriceOfProduct(final UUID productId, final BigDecimal price) throws Exception {
        try {
            List<Menu> menus = menuRepository.findAllByProductId(productId);

            menus.stream()
                    .forEach(a -> a.changeProductPrice(productId, price));

        } catch (Exception e) {
            throw new Exception("changePriceOfProduct fail - "+e.getMessage());
        }
    }
}

package kitchenpos.menus.application;

import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.application.ProductMenuService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ProductMenuServiceImpl implements ProductMenuService {

    private final MenuRepository menuRepository;

    public ProductMenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public void changeMenuDisplayStatus(final UUID productId) {
        menuRepository.findAllByProductId(productId)
                .forEach(menu -> {
                    BigDecimal sum = menu.getMenuProducts().stream()
                            .map(menuProduct -> menuProduct.getProduct().getPrice().getProductPrice().multiply(BigDecimal.valueOf(menuProduct.getQuantity())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    if (menu.getPrice().compareTo(sum) > 0) {
                        menu.setDisplayed(false);
                    }
                });
    }
}

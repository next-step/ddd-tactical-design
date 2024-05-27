package kitchenpos.products.tobe.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ProductEventHandler {

    private final MenuRepository menuRepository;

    public ProductEventHandler(final MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void productPriceChangeEvent(UUID productId) {

        final List<Menu> menus = menuRepository.findAllByProductId(productId);

        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;

            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                sum = sum.add(
                        menuProduct.getProduct()
                                .getPrice()
                                .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
                );
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }
    }

}

package kitchenpos.menus.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.domain.ProductPriceChangeEventProduct;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Component
public class ProductPriceChangeEventHandler {
    private final MenuRepository menuRepository;

    public ProductPriceChangeEventHandler(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Transactional
    @TransactionalEventListener
    public void handle(ProductPriceChangeEventProduct productPriceChangeEvent) {
        final List<Menu> menus = menuRepository.findAllByProductId(productPriceChangeEvent.getId());
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

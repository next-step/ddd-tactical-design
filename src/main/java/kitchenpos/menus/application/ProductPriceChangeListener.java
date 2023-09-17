package kitchenpos.menus.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.event.ProductPriceChangeEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
public class ProductPriceChangeListener {

    private final MenuValidator menuValidator;
    private final MenuRepository menuRepository;


    public ProductPriceChangeListener(MenuValidator menuValidator, MenuRepository menuRepository) {
        this.menuValidator = menuValidator;
        this.menuRepository = menuRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void hideMenuBasedOnMenuAndMenuProductPrice(ProductPriceChangeEvent event) {
        final List<Menu> menus = menuRepository.findAllByProductId(event.getProductId());

        menus.stream().filter(this::isMenuPriceGreaterThanSumOfMenuProducts)
                .forEach(Menu::hide);
    }

    private boolean isMenuPriceGreaterThanSumOfMenuProducts(Menu menu) {
        try {
            menuValidator.validatePrice(menu.getPrice(), menu.getMenuProducts());
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}

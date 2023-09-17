package kitchenpos.menus.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.dto.MenuDto;
import kitchenpos.menus.tobe.domain.ToBeMenu;
import kitchenpos.menus.tobe.domain.ToBeMenuRepository;
import kitchenpos.products.event.ProductPriceChangeEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.math.BigDecimal;
import java.util.List;

import static kitchenpos.common.util.ComparisonUtils.greaterThan;

@Component
public class ProductPriceChangeListener {

    private final MenuValidator menuValidator;
    private final ToBeMenuRepository menuRepository;


    public ProductPriceChangeListener(MenuValidator menuValidator, ToBeMenuRepository menuRepository) {
        this.menuValidator = menuValidator;
        this.menuRepository = menuRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void hideMenuBasedOnMenuAndMenuProductPrice(ProductPriceChangeEvent event) {
        final List<ToBeMenu> menus = menuRepository.findAllByProductId(event.getProductId());

        menus.stream().filter(this::isMenuPriceGreaterThanSumOfMenuProducts)
                .forEach(ToBeMenu::hide);
    }

    private boolean isMenuPriceGreaterThanSumOfMenuProducts(ToBeMenu menu) {
        try {
            menuValidator.validatePrice(menu.getPrice(), menu.getMenuProducts());
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}

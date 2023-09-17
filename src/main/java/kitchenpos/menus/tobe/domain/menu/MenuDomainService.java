package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.support.event.ProductPriceChangedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class MenuDomainService {

    private final MenuRepository menuRepository;

    public MenuDomainService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleProductPriceChangedEvent(ProductPriceChangedEvent event) {
        List<Menu> menus = findAllByProductId(event.getId());
        checkMenuCouldBeDisplayedAfterProductPriceChanged(menus);
    }

    public List<Menu> findAllByProductId(UUID productId) {
        return menuRepository.findAllByProductId(productId);
    }

    private void checkMenuCouldBeDisplayedAfterProductPriceChanged(List<Menu> menus) {
        menus.forEach(menu -> {
            BigDecimal totalMenuProductPrice = menu.getMenuProducts()
                    .parallelStream()
                    .map(menuProduct -> menuProduct.getProduct().getPrice())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (isMenuPriceGreaterThanSumOfMenuProducts(menu, totalMenuProductPrice)) {
                menu.hide();
            }
        });
    }

    private boolean isMenuPriceGreaterThanSumOfMenuProducts(Menu menu, BigDecimal totalMenuProductPrice) {
        return menu.getPrice().compareTo(totalMenuProductPrice) > 0;
    }

}

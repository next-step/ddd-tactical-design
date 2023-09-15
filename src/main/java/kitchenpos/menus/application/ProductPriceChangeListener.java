package kitchenpos.menus.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.event.ProductPriceChangeEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.math.BigDecimal;
import java.util.List;

import static kitchenpos.common.ComparisonUtils.greaterThan;

@Component
public class ProductPriceChangeListener {

    private final MenuRepository menuRepository;


    public ProductPriceChangeListener(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void hideMenuBasedOnMenuAndMenuProductPrice(ProductPriceChangeEvent event) {
        final List<Menu> menus = menuRepository.findAllByProductId(event.getProductId());
        menus.stream().filter(this::isMenuPriceGreaterThanSumOfMenuProducts)
                .forEach(e -> e.setDisplayed(false)); // TODO 향후 Menu의 도메인 로직으로 이관
    }

    private boolean isMenuPriceGreaterThanSumOfMenuProducts(Menu menu) {
        BigDecimal sum = menu.getMenuProducts().stream()
                .reduce(BigDecimal.ZERO, (acc, e) -> {
                    BigDecimal sumOfEachMenuProduct = e.getProduct().getPrice().multiply(e.getQuantity());
                    return acc.add(sumOfEachMenuProduct);
                }, BigDecimal::add);
        return greaterThan(menu.getPrice(), sum);
    }
}

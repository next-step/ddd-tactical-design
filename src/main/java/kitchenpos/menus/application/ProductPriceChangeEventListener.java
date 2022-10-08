package kitchenpos.menus.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.application.ProductPriceChangeEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ProductPriceChangeEventListener implements ApplicationListener<ProductPriceChangeEvent> {

    private final MenuRepository menuRepository;

    public ProductPriceChangeEventListener(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public void onApplicationEvent(ProductPriceChangeEvent event) {
        final List<Menu> menus = menuRepository.findAllByProductId(event.getProductId());
        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                menuProduct.changePrice(event.getPrice());
                sum = sum.add(
                        menuProduct
                                .getPrice()
                                .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
                );
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.changeDisplay(false);
            }
        }
    }
}

package kitchenpos.menus.domain;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class PriceAdjustment {

    private final MenuRepository menuRepository;

    public PriceAdjustment(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void changeMenuPrice(final UUID productId) {
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            sum = sum.add(getTotalPrice(menu));
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }
    }

    private BigDecimal getTotalPrice(Menu menu) {
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menu.getMenuProducts()) {
            sum = sum.add(
                    menuProduct.getProduct()
                            .getPrice().getPrice()
                            .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            );
        }
        return sum;
    }
}

package kitchenpos.common.domain;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.model.MenuProduct;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.product.domain.model.Product;
import org.springframework.stereotype.Service;

@Service
public class MarginValidator {

    private final MenuRepository menuRepository;

    public MarginValidator(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void checkMargin(Product product) {
        List<Menu> menus = menuRepository.findAllByProductId(product.getId());
        for (Menu menu : menus) {
            checkMargin(menu);
        }
    }

    private void checkMargin(Menu menu) {
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menu.getMenuProducts()) {
            sum = sum.add(
                    menuProduct.getProduct()
                            .getInnerPrice()
                            .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            );
        }
        if (menu.getPrice().compareTo(sum) < 0) {
            menu.changeDisplay();
        }
    }
}

package kitchenpos.products.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import kitchenpos.common.DomainService;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.domain.tobe.domain.ToBeProduct;
import kitchenpos.products.domain.tobe.domain.ToBeProductRepository;

@DomainService
public class PriceCompareWithMenuProductService {
    private final ToBeProductRepository toBeProductRepository;
    private final MenuRepository menuRepository;

    public PriceCompareWithMenuProductService(ToBeProductRepository toBeProductRepository,
        MenuRepository menuRepository) {
        this.toBeProductRepository = toBeProductRepository;
        this.menuRepository = menuRepository;
    }

    public void changeMenuDisplay(final UUID productId, final BigDecimal productPrice) {
        final ToBeProduct toBeProduct = toBeProductRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
        toBeProduct.changePrice(productPrice);

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

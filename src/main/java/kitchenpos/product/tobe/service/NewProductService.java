package kitchenpos.product.tobe.service;

import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuProduct;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.product.tobe.domain.port.inp.NewProductCreator;
import kitchenpos.product.tobe.domain.port.inp.NewProductPriceChanger;
import kitchenpos.product.tobe.domain.port.inp.NewProductPriceChangerCommand;
import kitchenpos.product.tobe.domain.port.inp.NewProductCreatorCommand;
import kitchenpos.product.tobe.domain.NewProduct;
import kitchenpos.product.tobe.domain.port.outp.NewProductRepository;
import kitchenpos.profanity.domain.PurgomalumChecker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
public class NewProductService implements NewProductCreator, NewProductPriceChanger {
    private final NewProductRepository newProductRepository;
    private final MenuRepository menuRepository;
    private final PurgomalumChecker purgomalumChecker;

    public NewProductService(
            final NewProductRepository newProductRepository,
            final MenuRepository menuRepository,
            final PurgomalumChecker purgomalumChecker
    ) {
        this.newProductRepository = newProductRepository;
        this.menuRepository = menuRepository;
        this.purgomalumChecker = purgomalumChecker;
    }

    @Override
    public NewProduct create(final NewProductCreatorCommand command) {
        final NewProduct product = NewProduct.createWithoutProfanity(command.getName(), command.getPrice(), purgomalumChecker);

        return newProductRepository.save(product);
    }

    @Override
    public NewProduct change(final NewProductPriceChangerCommand command) {
        final NewProduct product = newProductRepository.findById(command.getProductId())
                .orElseThrow(NoSuchElementException::new);

        product.changePrice(command.getPrice());

        /** 해당 관련 코드는 추후 MenuService로 이동 **/
        applyDisplayable(command.getProductId());

        return product;
    }

    private void applyDisplayable(final UUID productId) {
        final List<Menu> menus = menuRepository.findAllByProductId(productId);

        for (final Menu menu : menus) {
            if (isNotDisplayable(menu)) {
                menu.setDisplayed(false);
            }
        }
    }

    private boolean isNotDisplayable(final Menu menu) {
        BigDecimal sum = sumOfPrice(menu.getMenuProducts());

        return menu.getPrice().compareTo(sum) > 0;
    }

    private BigDecimal sumOfPrice(final List<MenuProduct> menuProducts) {
        return menuProducts.stream()
                .map(menuProduct ->
                        menuProduct.getProduct()
                                .getPrice()
                                .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

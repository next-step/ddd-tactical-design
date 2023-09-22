package kitchenpos.menu.application.menu;

import static kitchenpos.support.ParameterValidateUtils.checkNotNull;

import java.util.List;
import java.util.UUID;
import kitchenpos.menu.application.exception.NotExistProductException;
import kitchenpos.menu.application.menu.port.in.MenuProductPriceUpdateUseCase;
import kitchenpos.menu.application.menu.port.out.MenuRepository;
import kitchenpos.menu.application.menu.port.out.Product;
import kitchenpos.menu.application.menu.port.out.ProductFindPort;
import kitchenpos.menu.domain.menu.MenuNew;
import org.springframework.transaction.annotation.Transactional;

public class DefaultMenuProductPriceUpdateUseCase implements MenuProductPriceUpdateUseCase {

    private final MenuRepository repository;
    private final ProductFindPort productFindPort;

    public DefaultMenuProductPriceUpdateUseCase(final MenuRepository repository,
        final ProductFindPort productFindPort) {

        this.repository = repository;
        this.productFindPort = productFindPort;
    }

    @Transactional
    @Override
    public void update(final UUID productId) {
        checkNotNull(productId, "productId");

        final List<MenuNew> menus = repository.findAllByProductId(productId);

        final Product product = productFindPort.find(productId)
            .orElseThrow(() -> new NotExistProductException(productId));

        menus.forEach(menu -> menu.changeProductPrice(productId, product.getPrice()));
    }
}

package kitchenpos.menus.tobe.domain.application;

import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.products.tobe.domain.repository.ProductRepository;

public class DefaultProductPriceChangedEventHandlerTestFixture extends DefaultProductPriceChangedEventHandler {


    public DefaultProductPriceChangedEventHandlerTestFixture(MenuRepository menuRepository,
                                                             ProductRepository productRepository) {
        super(menuRepository, productRepository);
    }
}


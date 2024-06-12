package kitchenpos.menus.tobe.domain.application;

import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.products.tobe.domain.repository.ProductRepository;

public class HideMenuWithInvalidPriceByProductIdTestFixture extends DefaultHideMenuWithInvalidPriceByProductId {


    public HideMenuWithInvalidPriceByProductIdTestFixture(MenuRepository menuRepository,
                                                          ProductRepository productRepository) {
        super(menuRepository, productRepository);
    }
}


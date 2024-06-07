package kitchenpos.menus.tobe.domain.application;

import kitchenpos.menus.tobe.domain.repository.MenuRepository;

public class HideMenuWithInvalidPriceByProductIdTestFixture extends DefaultHideMenuWithInvalidPriceByProductId {
    public HideMenuWithInvalidPriceByProductIdTestFixture(MenuRepository menuRepository) {
        super(menuRepository);
    }
}


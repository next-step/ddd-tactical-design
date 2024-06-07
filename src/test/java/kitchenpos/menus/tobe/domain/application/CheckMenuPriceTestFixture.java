package kitchenpos.menus.tobe.domain.application;

import kitchenpos.menus.tobe.domain.repository.MenuRepository;

public class CheckMenuPriceTestFixture extends DefaultCheckMenuPrice {
    public CheckMenuPriceTestFixture(MenuRepository menuRepository) {
        super(menuRepository);
    }
}

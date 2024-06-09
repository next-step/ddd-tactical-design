package kitchenpos.menus.tobe.domain.application;

import kitchenpos.menus.tobe.domain.repository.MenuRepository;

public class ChangeMenuPriceTestFixture extends DefaultChangeMenuPrice {


    public ChangeMenuPriceTestFixture(MenuRepository menuRepository,
                                      CalculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity calculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity) {
        super(menuRepository, calculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity);
    }
}

package kitchenpos.menus.tobe.domain.fixture;

import kitchenpos.common.FakeProfanities;
import kitchenpos.common.domain.Name;
import kitchenpos.common.domain.Price;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.model.MenuGroup;
import kitchenpos.menus.tobe.domain.repository.InMemoryMenuGroupRepository;
import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.service.MenuService;

import java.math.BigDecimal;

public class MenuFixture {

    public static Menu MENU_FIXTURE(Long menuPriceValue, String nameValue, Long menuProductPriceValue, Long quantityValue) {
        MenuGroupRepository menuGroupRepository = new InMemoryMenuGroupRepository();
        MenuGroup menuGroup = new MenuGroup("치킨");
        menuGroupRepository.save(menuGroup);

        MenuService menuService = new MenuService(menuGroupRepository);
        return new Menu(menuGroup.getId(), new Price(BigDecimal.valueOf(menuPriceValue)), new Name(nameValue, new FakeProfanities()), MenuProductsFixture.MENU_PRODUCTS_FIXTURE_WITH_PRICE_AND_QUANTITY(menuProductPriceValue, quantityValue), menuService);
    }

}

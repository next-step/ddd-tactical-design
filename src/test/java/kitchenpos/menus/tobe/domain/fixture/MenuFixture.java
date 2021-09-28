package kitchenpos.menus.tobe.domain.fixture;

import kitchenpos.common.FakeProfanities;
import kitchenpos.common.domain.Name;
import kitchenpos.common.domain.Price;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.model.MenuGroup;
import kitchenpos.menus.tobe.domain.repository.InMemoryMenuGroupRepository;
import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.validator.MenuValidator;
import kitchenpos.products.tobe.domain.repository.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.repository.ProductRepository;

import java.math.BigDecimal;

public class MenuFixture {

    public static Menu MENU_FIXTURE(Long menuPriceValue, String nameValue, Long menuProductPriceValue, Long quantityValue) {
        MenuGroupRepository menuGroupRepository = new InMemoryMenuGroupRepository();
        ProductRepository productRepository = new InMemoryProductRepository();
        MenuGroup menuGroup = new MenuGroup(new Name("치킨", new FakeProfanities()));
        menuGroupRepository.save(menuGroup);

        MenuValidator menuValidator = new MenuValidator(menuGroupRepository, productRepository);
        return new Menu(menuGroup.getId(), new Price(BigDecimal.valueOf(menuPriceValue)), new Name(nameValue, new FakeProfanities()), MenuProductsFixture.MENU_PRODUCTS_FIXTURE_WITH_PRICE_AND_QUANTITY(menuProductPriceValue, quantityValue), menuValidator);
    }

}

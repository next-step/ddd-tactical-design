package kitchenpos.menus.tobe.fixtures;

import kitchenpos.menus.tobe.domain.*;
import kitchenpos.products.tobe.Money;
import kitchenpos.products.tobe.Name;

import java.util.List;
import java.util.UUID;

public class MenuStep {

    private MenuRepository menuRepository;
    private MenuGroupRepository menuGroupRepository;

    public MenuStep(MenuRepository menuRepository, MenuGroupRepository menuGroupRepository) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
    }

    public MenuGroup 메뉴_그룹_생성(UUID menuGroupId, Name name) {
        return menuGroupRepository.save(new MenuGroup(menuGroupId, name));
    }

    public MenuGroup 메뉴_그룹_생성() {
        return 메뉴_그룹_생성(UUID.randomUUID(), new Name("메뉴 그룹"));
    }


    public Menu 메뉴_생성(UUID menuId, Name name, Money money, MenuGroup menuGroup, boolean displayed, List<MenuProduct> menuProducts) {
        return menuRepository.save(new Menu(menuId, name, money, menuGroup, displayed, menuProducts));
    }

    public Menu 메뉴_생성() {
        Money menuPrice = Money.from(1000L);
        MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), 1L, menuPrice);
        return 메뉴_생성(UUID.randomUUID(), new Name("메뉴"), menuPrice, 메뉴_그룹_생성(), true, List.of(menuProduct));
    }

    public Menu 메뉴_생성(MenuProduct menuProduct) {
        return 메뉴_생성(UUID.randomUUID(), new Name("메뉴"), Money.from(1000L), 메뉴_그룹_생성(), true, List.of(menuProduct));
    }

    public Menu 메뉴_생성(MenuProduct menuProduct, Money menuPrice) {
        return 메뉴_생성(UUID.randomUUID(), new Name("메뉴"), menuPrice, 메뉴_그룹_생성(), true, List.of(menuProduct));
    }
}

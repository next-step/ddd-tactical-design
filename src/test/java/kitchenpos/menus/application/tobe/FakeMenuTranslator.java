package kitchenpos.menus.application.tobe;

import kitchenpos.menus.tobe.domain.TobeMenuProduct;
import kitchenpos.menus.tobe.infra.MenuTranslator;
import kitchenpos.menus.tobe.ui.MenuProductForm;

import java.util.List;

public class FakeMenuTranslator implements MenuTranslator {

    @Override
    public List<TobeMenuProduct> productFindAllByIdIn(List<MenuProductForm> menuProductForms) {
        return TobeMenusFixtures.menuProducts().getMenuProducts();
    }
}

package kitchenpos.menus.application.tobe;

import kitchenpos.menus.tobe.domain.TobeMenuProduct;
import kitchenpos.menus.tobe.infra.MenuAdaptor;
import kitchenpos.menus.tobe.ui.MenuProductForm;

import java.util.List;

public class FakeMenuAdaptor implements MenuAdaptor {

    @Override
    public List<TobeMenuProduct> productFindAllByIdIn(List<MenuProductForm> menuProductForms) {
        return TobeMenusFixtures.menuProducts().getMenuProducts();
    }
}

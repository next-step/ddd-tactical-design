package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.tobe.domain.TobeMenuProduct;
import kitchenpos.menus.tobe.domain.TobeProduct;
import kitchenpos.menus.tobe.ui.MenuProductForm;

import java.util.List;

public interface MenuTranslator {
    List<TobeMenuProduct> productFindAllByIdIn(List<MenuProductForm> menuProductForms);
}

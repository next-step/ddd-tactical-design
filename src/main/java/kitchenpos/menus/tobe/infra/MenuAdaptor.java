package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.tobe.domain.TobeMenuProduct;
import kitchenpos.menus.tobe.ui.MenuProductForm;

import java.util.List;

public interface MenuAdaptor {
    List<TobeMenuProduct> productFindAllByIdIn(List<MenuProductForm> menuProductForms);
}

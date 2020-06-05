package kitchenpos.menus.tobe.domain.menu;

import java.util.List;

public interface MenuProductFactory {

    List<MenuProduct> getMenuProducts(List<ProductQuantity> productQuantities);
}

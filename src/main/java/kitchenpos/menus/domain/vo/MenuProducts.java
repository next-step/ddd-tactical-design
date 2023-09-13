package kitchenpos.menus.domain.vo;

import kitchenpos.menus.domain.exception.InvalidMenuProductsException;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.model.MenuProductModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MenuProducts {
    final List<MenuProductModel> menuProducts;

    public MenuProducts(List<MenuProductModel> menuProducts) {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new InvalidMenuProductsException();
        }



        this.menuProducts = menuProducts;
    }
    public int size() {
        return menuProducts.size();
    }

    public BigDecimal totalAmount() {
        return menuProducts.stream()
                .map(MenuProductModel::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<MenuProduct> toMenuProducts() {
        return this.menuProducts.stream()
                .map(menuProductModel -> new MenuProduct(menuProductModel.getProductId(), menuProductModel.getQuantity()))
                .collect(Collectors.toList());


    }

}

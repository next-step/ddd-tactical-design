package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.application.MenuProductRequest;

import java.math.BigDecimal;
import java.util.List;

public class MenuProductCatalog {

    private final List<MenuProductInfo> entries;

    public MenuProductCatalog(List<MenuProductRequest> menuProductRequests, ProductInfos productInfos) {
        this.entries = menuProductRequests.stream()
                .map(menuProductRequest -> new MenuProductInfo(
                        productInfos.findById(menuProductRequest.productId()).id(),
                        productInfos.findById(menuProductRequest.productId()).price(),
                        new MenuProductQuantity(menuProductRequest.quantity())
                ))
                .toList();
    }

    public BigDecimal calculateTotalPrice() {
        return entries.stream()
                .map(menuProductInfo -> menuProductInfo.price().multiply(BigDecimal.valueOf(menuProductInfo.quantity().value())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<MenuProductInfo> getEntries() {
        return entries.stream().toList();
    }
}

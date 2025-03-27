package kitchenpos.menus.tobe.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Embeddable
public class MenuProducts {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProductList;

    protected MenuProducts() {
    }

    public MenuProducts(MenuProductCatalog menuProductCatalog) {
        this.menuProductList = menuProductCatalog.getEntries()
                .stream()
                .map(menuProductInfo -> new MenuProduct(menuProductInfo.id(), menuProductInfo.quantity()))
                .toList();
    }

    public List<UUID> getProductIds() {
        return menuProductList.stream()
                .map(MenuProduct::getProductId)
                .toList();
    }

    public MenuProduct getById(final UUID id) {
        return menuProductList.stream()
                .filter(menuProduct -> menuProduct.getProductId().equals(id))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    public BigDecimal calculateTotalPrice(ProductInfos productInfos) {
        return menuProductList.stream()
                .map(menuProduct -> {
                    ProductInfo productInfo = productInfos.findById(menuProduct.getProductId());
                    return productInfo.price().multiply(BigDecimal.valueOf(menuProduct.getQuantity().value()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

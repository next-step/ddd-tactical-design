package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.menus.ui.dto.MenuProductRequest;
import kitchenpos.products.tobe.domain.Product;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Embeddable
public class MenuProducts {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts;

    protected MenuProducts() {
    }

    public MenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public MenuProducts(List<MenuProductRequest> menuProductRequests, List<Product> products, Price menuPrice) {
        validation(menuProductRequests, products, menuPrice);

        this.menuProducts = menuProductRequests.stream()
                .map(menuProductRequest -> new MenuProduct(menuProductRequest.getProductId(), menuProductRequest.getQuantity()))
                .collect(Collectors.toList());
    }

    private void validation(List<MenuProductRequest> menuProductRequests, List<Product> products, Price menuPrice) {
        if (products.size() != menuProductRequests.size()) {
            throw new IllegalArgumentException("메뉴에 등록하려고 하는 상품이 존재하지 않습니다.");
        }

        if (menuPrice.compareTo(getMenuProductsTotalPrice(menuProductRequests, products)) > 0) {
            throw new IllegalArgumentException("메뉴의 가격은 상품의 총합보다 작거나 같아야합니다.");
        }
    }

    private Price getMenuProductsTotalPrice(List<MenuProductRequest> menuProductRequests, List<Product> products) {
        Price totalPrice = menuProductRequests.stream()
                .map(menuProductRequest -> new Price(getProductTotalPrice(products, menuProductRequest))
                ).reduce(new Price(BigDecimal.ZERO), Price::add);
        return totalPrice;
    }

    private BigDecimal getProductTotalPrice(List<Product> products, MenuProductRequest menuProductRequest) {
        return getProductPrice(products, menuProductRequest)
                .multiply(BigDecimal.valueOf(menuProductRequest.getQuantity()));
    }

    private BigDecimal getProductPrice(List<Product> products, MenuProductRequest menuProductRequest) {
        return products.stream()
                .filter(product -> product.isSameProductId(menuProductRequest.getProductId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("상품이 존재하지 않습니다."))
                .getPrice();
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public List<UUID> getProductIds() {
        return menuProducts.stream()
                .map(menuProduct -> menuProduct.getProductId())
                .collect(Collectors.toList());
    }

    public int size() {
        return menuProducts.size();
    }
}

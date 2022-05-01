package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.products.tobe.domain.Product;

import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
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

    public MenuProducts(List<MenuProductDto> menuProductDtos, List<Product> products, Price menuPrice) {
        validation(menuProductDtos, products, menuPrice);

        this.menuProducts = menuProductDtos.stream()
                .map(menuProductRequest -> new MenuProduct(menuProductRequest.getProductId(), menuProductRequest.getQuantity()))
                .collect(Collectors.toList());
    }

    private void validation(List<MenuProductDto> menuProductDtos, List<Product> products, Price menuPrice) {
        if (products.size() != menuProductDtos.size()) {
            throw new IllegalArgumentException("메뉴에 등록하려고 하는 상품이 존재하지 않습니다.");
        }

        if (menuPrice.compareTo(getMenuProductsTotalPrice(menuProductDtos, products)) > 0) {
            throw new IllegalArgumentException("메뉴의 가격은 상품의 총합보다 작거나 같아야합니다.");
        }
    }

    private Price getMenuProductsTotalPrice(List<MenuProductDto> menuProductDtos, List<Product> products) {
        Price totalPrice = menuProductDtos.stream()
                .map(menuProductDto -> new Price(getProductTotalPrice(products, menuProductDto))
                ).reduce(new Price(BigDecimal.ZERO), Price::add);
        return totalPrice;
    }

    private BigDecimal getProductTotalPrice(List<Product> products, MenuProductDto menuProductDtos) {
        return getProductPrice(products, menuProductDtos)
                .multiply(BigDecimal.valueOf(menuProductDtos.getQuantity()));
    }

    private BigDecimal getProductPrice(List<Product> products, MenuProductDto menuProductDtos) {
        return products.stream()
                .filter(product -> product.isSameProductId(menuProductDtos.getProductId()))
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

    public boolean isExpensiveMenuPrice(Price price, List<Product> findProducts) {
        for (final MenuProduct menuProduct : menuProducts) {
            BigDecimal productPrice = getMatchedProduct(findProducts, menuProduct).getPrice();
            final Price sum = new Price(productPrice.multiply(BigDecimal.valueOf(menuProduct.getQuantity())));
            if (price.compareTo(sum) > 0) {
                return true;
            }
        }
        return false;
    }

    private Product getMatchedProduct(List<Product> findProducts, MenuProduct menuProduct) {
        return findProducts.stream()
                .filter(product -> product.isSameProductId(menuProduct.getProductId()))
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }
}

package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName name;

    @Embedded
    private Price price;

    @Column(name = "menu_group_id", columnDefinition = "varbinary(16)", nullable = false)
    private UUID menuGroupId;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    public Menu() {
    }

    public Menu(PurgomalumClient purgomalumClient, UUID id, String name, Price price, UUID menuGroupId, boolean displayed, MenuProducts menuProducts) {
        this.id = id;
        this.name = new MenuName(purgomalumClient, name);
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public Menu(PurgomalumClient purgomalumClient, UUID id, String name, BigDecimal price, UUID menuGroupId, boolean displayed, MenuProducts menuProducts) {
        this.id = id;
        this.name = new MenuName(purgomalumClient, name);
        this.price = new Price(price);
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public Menu(PurgomalumClient purgomalumClient, String name, BigDecimal price, UUID menuGroupId, boolean displayed, MenuProducts menuProducts) {
        this.name = new MenuName(purgomalumClient, name);
        this.price = new Price(price);
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts.getMenuProducts();
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public void changePrice(Price price, List<Product> findProducts) {
        for (final MenuProduct menuProduct : menuProducts.getMenuProducts()) {
            BigDecimal productPrice = getMatchedProduct(findProducts, menuProduct).getPrice();
            final Price sum = new Price(productPrice.multiply(BigDecimal.valueOf(menuProduct.getQuantity())));
            if (price.compareTo(sum) > 0) {
                throw new IllegalArgumentException("메뉴의 가격은 상품가격의 총합보다 작거나 같아야합니다.");
            }
        }

        this.price = price;
    }

    public void show(List<Product> findProducts) {
        for (final MenuProduct menuProduct : menuProducts.getMenuProducts()) {
            BigDecimal productPrice = getMatchedProduct(findProducts, menuProduct).getPrice();
            final Price sum = new Price(productPrice.multiply(BigDecimal.valueOf(menuProduct.getQuantity())));
            if (price.compareTo(sum) > 0) {
                throw new IllegalStateException("메뉴의 가격이 상품의 가격보다 비쌀경우 메뉴를 노출할수 없습니다.");
            }
        }

        this.displayed = true;
    }

    private Product getMatchedProduct(List<Product> findProducts, MenuProduct menuProduct) {
        return findProducts.stream()
                .filter(product -> product.isSameProductId(menuProduct.getProductId()))
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }

    public void hide() {
        this.displayed = false;
    }

    public List<UUID> getProductIds() {
        return menuProducts.getProductIds();
    }
}

package kitchenpos.menu.tobe.domain.menu;

import jakarta.persistence.*;
import kitchenpos.common.tobe.Profanities;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName name;

    @Embedded
    private MenuPrice price;

    @Column(name = "menu_group_id", columnDefinition = "binary(16)", nullable = false)
    private UUID menuGroupId;

    @Embedded
    private MenuDisplayStatus displayed;

    private MenuProducts menuProducts;

    protected Menu() {
    }

    // 주 생성 메서드로 of 사용
    public static Menu of(String name, Long price, UUID menuGroupId, List<MenuProduct> products, boolean displayed, Profanities profanities, MenuValidator menuValidator) {
        return new Menu(
                UUID.randomUUID(),
                MenuName.of(name, profanities),
                MenuPrice.of(price),
                menuGroupId,
                MenuDisplayStatus.of(displayed),
                MenuProducts.of(products),
                menuValidator
        );
    }

    private Menu(UUID id, MenuName name, MenuPrice price, UUID menuGroupId, MenuDisplayStatus displayed, MenuProducts menuProducts, MenuValidator menuValidator) {
        menuValidator.validateMenuProductSize(menuProducts);
        menuValidator.validateMenuPrice(menuProducts, price);
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public Long getMenuPrice() {
        return price.getValue();
    }

    public UUID getId() {
        return id;
    }

    public void changeMenuPrice(final Long price, final MenuValidator menuValidator) {

        MenuPrice newPrice = MenuPrice.of(price);

        menuValidator.validateMenuPrice(menuProducts, newPrice);

        this.price = newPrice;
    }

    public void show(MenuValidator menuValidator) {
        menuValidator.validateMenuPrice(menuProducts, price);
        this.displayed.show();
    }

    public void hide() {
        this.displayed.hide();
    }

    public boolean isDisplayed() {
        return displayed.isDisplayed();
    }

    public String getName() {
        return name.getName();
    }

    public List<MenuProduct> getMenuProducts() {
        return Collections.unmodifiableList(menuProducts.getProducts());
    }

    public MenuProducts getMenuProduct() {
        return menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

}

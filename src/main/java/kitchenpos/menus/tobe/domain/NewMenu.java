package kitchenpos.menus.tobe.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kitchenpos.common.domain.DisplayNameChecker;
import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;
import kitchenpos.menus.tobe.domain.dto.MenuCreateDto;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static kitchenpos.menus.exception.MenuProductExceptionMessage.NOT_EQUAL_MENU_PRODUCT_SIZE;

@Table(name = "menu")
@Entity
public class NewMenu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName name;

    @Embedded
    private Price price;

//    @JoinColumn(
//            name = "menu_group_id",
//            nullable = false,
//            columnDefinition = "binary(16)",
//            foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
//    )
    @Column(name = "menu_group_id", nullable = false, columnDefinition = "binary(16)")
    private UUID menuGroupId;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;


    public NewMenu() {
    }

    public NewMenu(UUID id, DisplayedName name, Price price, UUID menuGroupId, boolean displayed, MenuProducts menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public static NewMenu create(UUID id, UUID menuGroupId, MenuProducts menuProducts, Price price, DisplayedName name, boolean displayed) {
        return new NewMenu(id, name, price, menuGroupId, displayed, menuProducts);
    }

    public static NewMenu createMenu(
            List<UUID> productIds, Function<List<UUID>, Map<UUID, BigDecimal>> func, UUID id,
            DisplayNameChecker displayNameChecker, UUID menuGroupId, MenuCreateDto menuCreateDto) {

        Price menuPrice = Price.of(menuCreateDto.getPrice());
        DisplayedName menuName = DisplayedName.of(menuCreateDto.getName(), displayNameChecker);

        Map<UUID, BigDecimal> productPriceMap = func.apply(productIds);
        validateExistProduct(productIds, productPriceMap);

        MenuProducts menuProducts = MenuProducts.create(menuCreateDto.getProductQuantityMap());
        menuProducts.validateMenuPrice(productPriceMap, menuPrice);

        return NewMenu.create(id, menuGroupId, menuProducts, menuPrice, menuName, menuCreateDto.isDisplayed());
    }

    private static void validateExistProduct(List<UUID> productIds, Map<UUID, BigDecimal> productPriceMap) {
        if (productIds.size() != productPriceMap.size()) {
            throw new IllegalArgumentException(NOT_EQUAL_MENU_PRODUCT_SIZE);
        }
    }

    public void displayed(Map<UUID, BigDecimal> productPriceMap) {
        menuProducts.validateMenuPrice(productPriceMap, price);
        this.displayed = true;
    }

    public void notDisplayed() {
        this.displayed = false;
    }


    public boolean isDisplayed() {
        return displayed;
    }

    public void changePrice(Map<UUID, BigDecimal> productPriceMap, BigDecimal menuPrice) {
        Price price = Price.of(menuPrice);
        menuProducts.validateMenuPrice(productPriceMap, price);
        this.price = price;
    }

    @JsonIgnore
    public List<UUID> getMenuProductIds() {
        return menuProducts.getMenuProductIds();
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public List<NewMenuProduct> getMenuProductList() {
        return menuProducts.getMenuProducts();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }
}

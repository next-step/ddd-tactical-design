package kitchenpos.menus.tobe.domain.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.menus.tobe.domain.service.MenuDomainService;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    @Column(name = "name", nullable = false)
    private DisplayedName displayedName;

    @Embedded
    @Column(name = "price", nullable = false)
    private Price price;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Column(name = "menu_group_id", nullable = false)
    private UUID menuGroupId;

    @Embedded
    private MenuProducts menuProducts;

    public Menu() {
    }

    public Menu(String displayedName, BigDecimal price, boolean displayed, UUID menuGroupId, List<MenuProduct> menuProducts, MenuDomainService menuDomainService) {

        final DisplayedName newDisplayedName = new DisplayedName(displayedName, menuDomainService);
        final Price newPrice = new Price(price);
        menuDomainService.validateMenuGroup(menuGroupId);
        final MenuProducts newMenuProducts = new MenuProducts(menuProducts, menuDomainService);
        menuDomainService.validateMenuPrice(price, menuProducts);

        this.id = UUID.randomUUID();
        this.displayedName = newDisplayedName;
        this.price = newPrice;
        this.displayed = displayed;
        this.menuGroupId = menuGroupId;
        this.menuProducts = newMenuProducts;
    }

    public void changePrice(BigDecimal price, MenuDomainService menuDomainService) {
        Price changePrice = new Price(price);
        menuDomainService.validateMenuPrice(price, menuProducts.getMenuProducts());
        this.price = changePrice;
    }

    public void display(MenuDomainService menuDomainService) {
        menuDomainService.validateMenuPrice(price.getPrice(), menuProducts.getMenuProducts());
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public UUID getId() {
        return id;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public String getDisplayedName() {
        return displayedName.getName();
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts.getMenuProducts();
    }
}


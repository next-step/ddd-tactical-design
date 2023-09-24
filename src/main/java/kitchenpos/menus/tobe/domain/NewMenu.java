package kitchenpos.menus.tobe.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

    @Column(name = "menu_group_id", nullable = false, columnDefinition = "binary(16)")
    private UUID menuGroupId;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;


    public NewMenu() {
    }

    private NewMenu(UUID id, DisplayedName name, Price price, UUID menuGroupId, boolean displayed, MenuProducts menuProducts) {
        menuProducts.validateMenuPrice(price);

        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public static NewMenu create(UUID menuId, UUID menuGroupId, DisplayedName name, Price price, MenuProducts menuProducts, boolean displayed) {
        return new NewMenu(menuId, name, price, menuGroupId, displayed, menuProducts);
    }

    public void displayed() {
        menuProducts.validateMenuPrice(price);
        this.displayed = true;
    }

    public void notDisplayed() {
        this.displayed = false;
    }

    public void changePrice(Price price) {
        menuProducts.validateMenuPrice(price);
        this.price = price;
    }

    @JsonIgnore
    public List<UUID> getMenuProductIds() {
        return menuProducts.getMenuProductIds();
    }

    public BigDecimal getPriceValue() {
        return price.getPrice();
    }

    public Price getPrice() {
        return price;
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

    public boolean isDisplayed() {
        return displayed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewMenu newMenu = (NewMenu) o;
        return Objects.equals(id, newMenu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

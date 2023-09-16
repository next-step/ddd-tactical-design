package kitchenpos.menus.tobe.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.Price;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
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

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "menu_group_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private NewMenuGroup newMenuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;


    public NewMenu() {}

    public NewMenu(UUID id, DisplayedName name, Price price, NewMenuGroup newMenuGroup, boolean displayed, MenuProducts menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.newMenuGroup = newMenuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public static NewMenu create(UUID id, NewMenuGroup newMenuGroup, MenuProducts menuProducts, Price price, DisplayedName name, boolean displayed) {
        return new NewMenu(id, name, price, newMenuGroup, displayed, menuProducts);
    }

    @JsonIgnore
    public List<UUID> getMenuProductIds() {
        return menuProducts.getMenuProductIds();
    }

    public void displayed() {
        this.displayed = true;
    }

    public void notDisplayed() {
        this.displayed = false;
    }


    public boolean isDisplayed() {
        return displayed;
    }

    public void changePrice(BigDecimal price) {
        this.price = Price.of(price);
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
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

    public NewMenuGroup getMenuGroup() {
        return newMenuGroup;
    }
}

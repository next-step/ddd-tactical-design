package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    @Column(name = "name", nullable = false)
    private MenuName name;

    @Embedded
    @Column(name = "price", nullable = false)
    private MenuPrice price;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "menu_group_id",
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    @Transient
    private UUID menuGroupId;

    protected Menu() {
    }

    public Menu(MenuName name, MenuPrice price, MenuGroup menuGroup, boolean displayed, MenuProducts menuProducts) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public MenuName name() {
        return name;
    }

    public String nameValue() {
        return name.name();
    }

    public void setName(MenuName name) {
        this.name = name;
    }

    public MenuPrice price() {
        return price;
    }

    public BigDecimal priceValue() {
        return price.price();
    }

    public void setPrice(MenuPrice price) {
        this.price = price;
    }

    public MenuGroup menuGroup() {
        return menuGroup;
    }

    public void setMenuGroup(MenuGroup menuGroup) {
        this.menuGroup = menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    public MenuProducts menuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(MenuProducts menuProducts) {
        this.menuProducts = menuProducts;
    }

    public UUID menuGroupId() {
        return menuGroupId;
    }

    public void setMenuGroupId(UUID menuGroupId) {
        this.menuGroupId = menuGroupId;
    }
}

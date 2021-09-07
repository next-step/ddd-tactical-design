package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Table(name = "menu")
@Entity(name = "TobeMenu")
public class Menu {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName menuName;

    @Embedded
    private MenuPrice menuPrice;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "menu_group_id",
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts;

    @Transient
    private UUID menuGroupId;

    public Menu() {}

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return menuName.getName();
    }

    public void setName(final String name) {
        this.menuName = new MenuName(name);
    }

    public BigDecimal getPrice() {
        return menuPrice.getPrice();
    }

    public void setPrice(final BigDecimal price) {
        this.menuPrice = new MenuPrice(price);
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public void setMenuGroup(final MenuGroup menuGroup) {
        this.menuGroup = menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(final boolean displayed) {
        this.displayed = displayed;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(final List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public void setMenuGroupId(final UUID menuGroupId) {
        this.menuGroupId = menuGroupId;
    }

    public void validateName(final Function<String, Boolean> validator) {
        menuName.validate(validator);
    }
}

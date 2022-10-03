package kitchenpos.menus.domain;

import java.util.ArrayList;
import java.util.Objects;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {

    @Id
    @Column(
        name = "id",
        length = 16,
        unique = true,
        nullable = false
    )
    private UUID id;

    @Embedded
    private MenuName name;

    @Embedded
    private MenuPrice price;

    @ManyToOne
    @JoinColumn(name = "menu_group_id", nullable = false, columnDefinition = "binary(16)")
    private MenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "menu_id", nullable = false, columnDefinition = "binary(16)")
    private List<MenuProduct> menuProducts = new ArrayList<>();

    protected Menu() {
    }

    public Menu(
        MenuName name,
        MenuPrice price,
        MenuGroup menuGroup
    ) {
        this(UUID.randomUUID(), name, price, menuGroup);
    }

    public Menu(
        UUID id,
        MenuName name,
        MenuPrice price,
        MenuGroup menuGroup
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
    }

    public void addMenuProduct(MenuProduct menuProduct) {
        if (isNotContains(menuProduct)) {
            this.menuProducts.add(menuProduct);
        }
    }

    private boolean isNotContains(MenuProduct menuProduct) {
        return !menuProducts.contains(menuProduct);
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getNameValue() {
        return name.getValue();
    }

    public void setName(final String name) {

    }

    public BigDecimal getPriceValue() {
        return price.getValue();
    }

    public void setPrice(final BigDecimal price) {

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
        return menuGroup.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

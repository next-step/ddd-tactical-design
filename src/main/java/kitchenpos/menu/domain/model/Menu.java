package kitchenpos.menu.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {
    private static final String MENU_PRODUCTS_EXISTS_EXCEPTION = "메뉴 상품이 존재하지 않습니다!";
    private static final String MENU_GROUP_EXISTS_EXCEPTION = "메뉴 그룹이 존재하지 않습니다!";

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName name;

    @Embedded
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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts;

    @Transient
    private UUID menuGroupId;

    public Menu() {
    }

    public Menu(UUID id, String name, BigDecimal price, boolean displayed) {
        this.id = id;
        this.name = new MenuName(name);
        this.price = new MenuPrice(price);
        this.displayed = displayed;
    }

    public Menu(UUID id, MenuName name, MenuPrice price, MenuGroup menuGroup, boolean displayed,
                List<MenuProduct> menuProducts, UUID menuGroupId) {
        validateMenuGroupExists(menuGroup);
        validateMenuProductsExists(menuProducts);
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
        this.menuGroupId = menuGroupId;
    }

    public Menu(UUID id, String name, BigDecimal price, boolean displayed, List<MenuProduct> menuProducts,
                MenuGroup menuGroup,
                UUID menuGroupId) {
        this(id, new MenuName(name), new MenuPrice(price), menuGroup, displayed, menuProducts, menuGroupId);
    }

    public Menu(String name, BigDecimal price, boolean displayed, List<MenuProduct> menuProducts, MenuGroup menuGroup,
                UUID menuGroupId) {
        this(UUID.randomUUID(), new MenuName(name), new MenuPrice(price), menuGroup, displayed, menuProducts,
                menuGroupId);
    }

    private void validateMenuGroupExists(MenuGroup menuGroup) {
        if (menuGroup == null) {
            throw new NoSuchElementException(MENU_GROUP_EXISTS_EXCEPTION);
        }
    }

    private void validateMenuProductsExists(List<MenuProduct> menuProducts) {
        if (menuProducts == null || menuProducts.isEmpty()) {
            throw new NoSuchElementException(MENU_PRODUCTS_EXISTS_EXCEPTION);
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getInnerName() {
        return name.getValue();
    }

    public MenuName getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = new MenuName(name);
    }

    public BigDecimal getInnerPrice() {
        return price.getValue();
    }

    public MenuPrice getPrice() {
        return price;
    }

    public void changePrice(final BigDecimal price) {
        this.price = new MenuPrice(price);
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void changeDisplay(final boolean displayed) {
        this.displayed = displayed;
    }

    public void changeDisplay() {
        this.displayed = !this.displayed;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }
}

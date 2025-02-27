package kitchenpos.menu.domain.model;

import static kitchenpos.menu.exception.MenuExceptionMessage.MENU_GROUP_EXISTS_EXCEPTION;
import static kitchenpos.menu.exception.MenuExceptionMessage.MENU_PRODUCTS_EXISTS_EXCEPTION;

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
import org.springframework.data.domain.AbstractAggregateRoot;

@Table(name = "menu")
@Entity
public class Menu extends AbstractAggregateRoot<Menu> {
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

    public Menu(String name, BigDecimal price, boolean displayed) {
        this.id = UUID.randomUUID();
        this.name = new MenuName(name);
        this.price = new MenuPrice(price);
        this.displayed = displayed;
        // test 용 생성자
//        registerEvent(MenuSummaryEvent.from(this));
    }

    public Menu(MenuName name, MenuPrice price, MenuGroup menuGroup, boolean displayed,
                List<MenuProduct> menuProducts, UUID menuGroupId) {
        validateMenuGroupExists(menuGroup);
        validateMenuProductsExists(menuProducts);
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
        this.menuGroupId = menuGroupId;
        registerEvent(MenuSummaryEvent.from(this));
    }

    public Menu(String name, BigDecimal price, boolean displayed, List<MenuProduct> menuProducts,
                MenuGroup menuGroup,
                UUID menuGroupId) {
        this(new MenuName(name), new MenuPrice(price), menuGroup, displayed, menuProducts, menuGroupId);
    }

    private void validateMenuGroupExists(MenuGroup menuGroup) {
        if (menuGroup == null) {
            throw new NoSuchElementException(MENU_GROUP_EXISTS_EXCEPTION.getMessage());
        }
    }

    private void validateMenuProductsExists(List<MenuProduct> menuProducts) {
        if (menuProducts == null || menuProducts.isEmpty()) {
            throw new NoSuchElementException(MENU_PRODUCTS_EXISTS_EXCEPTION.getMessage());
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
        registerEvent(MenuSummaryEvent.from(this));
    }

    public String getInnerName() {
        return name.getValue();
    }

    public MenuName getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = new MenuName(name);
        registerEvent(MenuSummaryEvent.from(this));
    }

    public BigDecimal getInnerPrice() {
        return price.getValue();
    }

    public MenuPrice getPrice() {
        return price;
    }

    public void changePrice(final BigDecimal price) {
        this.price = new MenuPrice(price);
        registerEvent(MenuSummaryEvent.from(this));
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void changeDisplay(final boolean displayed) {
        this.displayed = displayed;
        registerEvent(MenuSummaryEvent.from(this));
    }

    public void changeDisplay() {
        this.displayed = !this.displayed;
        registerEvent(MenuSummaryEvent.from(this));
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }
}
